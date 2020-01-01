package com.yu.tools.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yu.tools.dao.ConnectDB;

public class CompareDataBase {
	static Logger logger  =  Logger.getLogger(CompareDataBase. class);
	public static void compareDateBase(ConnectDB targetDataBase,ConnectDB originDataBase,String schema,String workspace) throws Exception {

		List<String> tableNames = new ArrayList<String>();
		{
			String sql = "select table_name from information_schema.tables where table_schema='"+schema+"' and (table_type='base table' or table_type='BASE TABLE')";
			List<String> oldFields = new ArrayList<String>();
			oldFields.add("table_name");
			List<Map<String, String>> result = targetDataBase.getRowBySql(oldFields, sql);
			for(Map<String, String> ob : result) {
				tableNames.add(ob.get("table_name"));
			}
		}
		List<CompareTableExecutor> executors = new ArrayList<CompareTableExecutor>();

		for(String tableName : tableNames) {
			CompareTableExecutor executor = new CompareTableExecutor(targetDataBase,originDataBase,tableName,schema,workspace);
			executors.add(executor);
		}

		for(CompareTableExecutor executor : executors) {
			executor.run();
			//System.gc();
		}
	}
}
