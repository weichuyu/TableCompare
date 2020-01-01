package com.yu.tools.poi;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteXlsTemplateUtil {    
    //导出xlsx
    public static void WriteExcel(String printPath,List<Map<String,String>> data,List<DualData> header) throws Exception{
    	ArrayList<CellData> rowData = new ArrayList<CellData>();
    	Integer currentWriteNum = 0;
    	//poi写book
    	XSSFWorkbook wb = new XSSFWorkbook();
        //targetStyle
        XSSFCellStyle targetStyle = wb.createCellStyle();
        targetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFFont font3 = wb.createFont(); 
        font3.setFontName("微软雅黑");
        font3.setFontHeightInPoints((short) 10);
        targetStyle.setFont(font3);
        targetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        targetStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        targetStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        targetStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    	//写sheet
        XSSFSheet sheet2 = wb.createSheet("data");
    	//设列宽
    	sheet2.setDefaultColumnWidth(15);
    	for(int i=0;i<header.size();i++){
    		String width = header.get(i).width;
    		int colwidth = 15*256;
    		try{
    			colwidth = Integer.parseInt(width)*256;
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		sheet2.setColumnWidth(i, colwidth/*(field.width/4)*256*/);
    	}
    	//写头
    	for(DualData field:header){
    		rowData.add(new CellData(field.headerName,false));
    	}
    	writeRow(sheet2,rowData,targetStyle,currentWriteNum);
    	currentWriteNum++;
    	//写值
    	if(data == null || data.size() == 0){
    		for(DualData field:header){
        		rowData.add(new CellData(null,false));
        	}
        	writeRow(sheet2,rowData,targetStyle,currentWriteNum);
        	currentWriteNum++;
    	}else{
    		for(Map<String,String> row: data){
    			for(DualData field:header){
            		rowData.add(new CellData(row.get(field.dataName),false));
            	}
            	writeRow(sheet2,rowData,targetStyle,currentWriteNum);
            	currentWriteNum++;
    		}
    	}
    	//写文件
    	OutputStream osw = new FileOutputStream(printPath);
		wb.write(osw);
		osw.flush();
        osw.close();
    }
    
    private static void writeRow(XSSFSheet sheet,List<CellData> rowData,XSSFCellStyle headStyle,Integer currentWriteNum){
    	Row r = sheet.createRow(currentWriteNum);
    	for (int i = 0; i < rowData.size(); i++) {
    		XSSFCell cell = (XSSFCell) r.createCell(i);
			if(headStyle!=null){
				cell.setCellStyle(headStyle);
			}
			if(rowData.get(i).isNumeric()){
				cell.setCellValue(Double.parseDouble(rowData.get(i).getText()));
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			}else{
				cell.setCellValue(rowData.get(i).getText());
			}
		}
    	rowData.clear();
    }
    
    //导出xlsx的一个sheet
    public static void WriteExcel(XSSFWorkbook wb,String sheetName,List<Map<String,String>> data,List<DualData> header) throws Exception{
    	ArrayList<CellData> rowData = new ArrayList<CellData>();
    	Integer currentWriteNum = 0;
    	//poi写book
        //targetStyle
        XSSFCellStyle targetStyle = wb.createCellStyle();
        targetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFFont font3 = wb.createFont(); 
        font3.setFontName("微软雅黑");
        font3.setFontHeightInPoints((short) 10);
        targetStyle.setFont(font3);
        targetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        targetStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        targetStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        targetStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    	//写sheet
        XSSFSheet sheet2 = wb.createSheet(sheetName);
    	//设列宽
    	sheet2.setDefaultColumnWidth(15);
    	for(int i=0;i<header.size();i++){
    		String width = header.get(i).width;
    		int colwidth = 15*256;
    		try{
    			colwidth = Integer.parseInt(width)*256;
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		sheet2.setColumnWidth(i, colwidth/*(field.width/4)*256*/);
    	}
    	//写头
    	for(DualData field:header){
    		rowData.add(new CellData(field.headerName,false));
    	}
    	writeRow(sheet2,rowData,targetStyle,currentWriteNum);
    	currentWriteNum++;
    	//写值
    	if(data == null || data.size() == 0){
    		for(DualData field:header){
        		rowData.add(new CellData(null,false));
        	}
        	writeRow(sheet2,rowData,targetStyle,currentWriteNum);
        	currentWriteNum++;
    	}else{
    		for(Map<String,String> row: data){
    			for(DualData field:header){
    				if(row.get(field.dataName)!=null){
    					rowData.add(new CellData(row.get(field.dataName),false));
    				}else{
    					rowData.add(new CellData("",false));
    				}
            		
            	}
            	writeRow(sheet2,rowData,targetStyle,currentWriteNum);
            	currentWriteNum++;
    		}
    	}
    }
}
