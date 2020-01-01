package com.yu.tools.service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.yu.tools.dao.ConnectDB;
import com.yu.tools.dao.DualKey;
import com.yu.tools.dao.TripleKey;
import com.yu.tools.poi.DualData;
import com.yu.tools.poi.WriteXlsTemplateUtil;

public class CompareTableExecutor implements Runnable{
	static Logger logger  =  Logger.getLogger(CompareTableExecutor. class);
	private ConnectDB targetDataBase;
	private ConnectDB originDataBase;
	private String tableName;
	private String schemaName;
	private String workspace = "";

	public CompareTableExecutor(ConnectDB targetDataBase, ConnectDB originDataBase, String tableName,String schemaName,String workspace) {
		super();
		this.targetDataBase = targetDataBase;
		this.originDataBase = originDataBase;
		this.tableName = tableName;
		this.schemaName = schemaName;
		this.workspace = workspace;
	}

	public void run() {
		// TODO Auto-generated method stub
		logger.info("表名："+tableName);
		//获得目标表的主键名称
		List<String> key = new ArrayList<String>();
		List<String> fields = new ArrayList<String>();
		try{
			String sql = "select column_name,column_key,column_type,data_type from information_schema.columns where table_schema='"+schemaName+"' and table_name='"+tableName+"'";
			List<String> oldFields = new ArrayList<String>();
			oldFields.add("column_name");
			oldFields.add("column_key");
			oldFields.add("column_type");
			oldFields.add("data_type");
			List<Map<String, String>> result = targetDataBase.getRowBySql(oldFields, sql);
			for(Map<String, String> ob : result) {
				//某些字段就不读到内存了
				if("blob".equals(ob.get("data_type"))) {
					continue;
				}
				fields.add(ob.get("column_name"));
				if("PRI".equals(ob.get("column_key"))) {
					key.add(ob.get("column_name"));
				}
			}
		}catch(Exception e) {
			logger.error("tableNames:"+tableName+" error:"+e.getMessage(),e);
			return;
		}
//		logger.info("主键：");
//		for(String a : key) {
//			logger.info(a);
//		}
//		logger.info("字段：");
//		for(String a : fields) {
//			logger.info(a);
//		}
		if(key!=null && (key.size() == 1 || key.size() == 2 || key.size() == 3)) {
		}else {
			logger.error("tableNames:"+tableName+" error:"+"主键的数量必须是1，2，3。其他的没写");
			return;
		}
		//比较单个表的数据差异
		List<Map<String,String>> addData = new ArrayList<Map<String,String>>();
		List<Map<String,String>> modifiedData = new ArrayList<Map<String,String>>();
		List<Map<String,String>> deleteData = new ArrayList<Map<String,String>>();

		Map<Object,Map<String,String>> currentData = this.fetchKeyMapData(targetDataBase,key,fields,tableName);
		Map<Object,Map<String,String>> formerData = this.fetchKeyMapData(originDataBase,key,fields,tableName);
		//目标表 比 原始表 新增的记录
		//目标表 比 原始表 变更的记录
		for(Object rowKey : currentData.keySet()) {
			Map<String,String> newrow = currentData.get(rowKey);
			//如果老数据有，就开始比较字段
			if(formerData.containsKey(rowKey)) {
				Map<String,String> oldrow = formerData.get(rowKey);
				for(String field : fields) {
					String newfield = newrow.get(field);
					String oldfield = oldrow.get(field);
					if(newfield == null && oldfield == null) {
						continue;
					}else if(newfield!=null && newfield.equals(oldfield)) {
						continue;
					}
					Map<String,String> tmp = new HashMap<String,String>();
					tmp.put("keycolumn",JSONObject.toJSONString(key));
					tmp.put("key",JSONObject.toJSONString(rowKey));
					tmp.put("field",field);
					tmp.put("new",newfield);
					tmp.put("old",oldfield);
					modifiedData.add(tmp);
				}
			}else {
				addData.add(newrow);
				//说明是新记录
			}
		}
		//目标表 比 原始表 减少的记录
		for(Object rowKey : formerData.keySet()) {
			Map<String,String> oldrow = formerData.get(rowKey);
			//如果老数据有，就开始比较字段
			if(currentData.containsKey(rowKey)) {
				//do nothing
			}else {
				//说明是删除记录
				deleteData.add(oldrow);
			}
		}


		//如果存在差异，则生成excel 3个sheet 前面三个加上 目标表数据字典
		if(addData.size()!=0 || modifiedData.size()!=0 || deleteData.size()!=0) {
			List<DualData> header1 = new ArrayList<DualData>();
			for(String field : fields) {
				DualData tmp = new DualData();
				tmp.dataName = field;
				tmp.headerName = field;
				tmp.serviceName = "1";
				header1.add(tmp);
			}
			List<DualData> header2 = new ArrayList<DualData>();
			{
				DualData tmp = new DualData();
				tmp.dataName = "keycolumn";
				tmp.headerName = "主键列名";
				tmp.serviceName = "1";
				header2.add(tmp);
			}
			{
				DualData tmp = new DualData();
				tmp.dataName = "key";
				tmp.headerName = "主键";
				tmp.serviceName = "1";
				header2.add(tmp);
			}
			{
				DualData tmp = new DualData();
				tmp.dataName = "field";
				tmp.headerName = "字段";
				tmp.serviceName = "1";
				header2.add(tmp);
			}
			{
				DualData tmp = new DualData();
				tmp.dataName = "old";
				tmp.headerName = "旧值";
				tmp.serviceName = "1";
				header2.add(tmp);
			}
			{
				DualData tmp = new DualData();
				tmp.dataName = "new";
				tmp.headerName = "新值";
				tmp.serviceName = "1";
				header2.add(tmp);
			}
			//导出excel
			try {
				String printPath = workspace + tableName + ".xlsx";
				XSSFWorkbook wb = new XSSFWorkbook();
				if(addData.size()!=0) {
					WriteXlsTemplateUtil.WriteExcel(wb, "新增记录", addData, header1);
				}
				if(deleteData.size()!=0) {
					WriteXlsTemplateUtil.WriteExcel(wb, "删除记录", deleteData, header1);
				}
				if(modifiedData.size()!=0) {
					WriteXlsTemplateUtil.WriteExcel(wb, "变更记录", modifiedData, header2);
				}
				OutputStream osw = new FileOutputStream(printPath);
				wb.write(osw);
				osw.flush();
				osw.close();
			}catch(Exception e) {
				logger.error("tableNames:"+tableName+" error:"+e.getMessage(),e);
				return;
			}
		}
	}
	private Map<Object,Map<String,String>> fetchKeyMapData(ConnectDB dataBase,List<String> key,List<String> fields,String tableName){
		Map<Object,Map<String,String>> data = new HashMap<Object,Map<String,String>>();
		try{
			List<Map<String, String>> result = dataBase.getRow(fields, tableName);
			for(Map<String, String> ob : result) {
				Object rowkey = null;
				if(key!=null && key.size() == 1) {
					rowkey = ob.get(key.get(0));
				}else if(key!=null && key.size() == 2){
					DualKey tmp = new DualKey();
					tmp.setKey1(ob.get(key.get(0)));
					tmp.setKey2(ob.get(key.get(1)));
					rowkey = tmp;
				}else if(key!=null && key.size() == 3){
					TripleKey tmp = new TripleKey();
					tmp.setKey1(ob.get((key.get(0))));
					tmp.setKey2(ob.get(key.get(1)));
					tmp.setKey3(ob.get((key.get(2))));
					rowkey = tmp;
				}else {
					logger.error("tableNames:"+tableName+" error:"+"主键的数量必须是1，2，3。其他的没写");
				}
				data.put(rowkey, ob);
			}
		}catch(Exception e) {
			logger.error("tableNames:"+tableName+" error:"+e.getMessage(),e);
		}
		return data;
	}
}
