package com.yu.tools;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.yu.tools.dao.ConnectDB;
import com.yu.tools.service.CompareDataBase;

public class DataCompareApp {
	static Logger logger  =  Logger.getLogger(DataCompareApp. class);
	public static String url1;
	public static String user1;
	public static String password1;
	
	public static String url2;
	public static String user2;
	public static String password2;

	public static String schema;
	public static String output;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		initParam();
		ConnectDB targetDataBase = new ConnectDB();
		targetDataBase.local_url = url1;
		targetDataBase.local_user = user1;
		targetDataBase.local_password = password1;
		ConnectDB originDataBase = new ConnectDB();
		originDataBase.local_url = url2;
		originDataBase.local_user = user2;
		originDataBase.local_password = password2;
		CompareDataBase.compareDateBase(targetDataBase, originDataBase,schema,output);
		System.out.println("FIN");
	}
	
	private static void initParam(){
		ResourceBundle resource = ResourceBundle.getBundle("config");
		url1 = resource.getString("config.dataSourceTarget.url");
		user1 = resource.getString("config.dataSourceTarget.username");
		password1 = resource.getString("config.dataSourceTarget.password");
		
		url2 = resource.getString("config.dataSourceOrigin.url");
		user2 = resource.getString("config.dataSourceOrigin.username");
		password2 = resource.getString("config.dataSourceOrigin.password");

		schema = resource.getString("config.schema");
		output = resource.getString("config.output");
	}
}
