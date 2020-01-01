package com.yu.tools.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConnectDB {
	public String local_url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:orcl2";
	public String local_user = "hr2";
	public String local_password = "aaa2";
	public String driverName = "com.mysql.jdbc.Driver";
	
	public List<Map<String, String>> getRow(List<String> oldFields,String tableName) throws Exception {
		List<Map<String, String>> result2 = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		Class.forName(driverName);
		String url = local_url;
		String user = local_user;
		String password = local_password;
		con = DriverManager.getConnection(url, user, password);
		String fieldsStr="";
		for(int i=0;i<oldFields.size();i++){
			String flag = ",";
			if(i==oldFields.size()-1){
				flag="";
			}
			fieldsStr = fieldsStr+"t."+oldFields.get(i)+flag;
		}
		String sql = "select "+fieldsStr+" from "+tableName+" t";
		try {
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				Map<String, String> ob = new HashMap<String,String>();
				for(int i=0;i<oldFields.size();i++){
					String v = (result.getString(oldFields.get(i)));
					ob.put(oldFields.get(i), v);
				}
				result2.add(ob);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (result != null) {
				result.close();
			}
			if (pre != null) {
				pre.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return result2;
	}
	
	public List<Map<String, String>> getRow(List<String> oldFields,String tableName,String wherePhrase,String orderPhrase) throws Exception {
		List<Map<String, String>> result2 = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		Class.forName(driverName);
		String url = local_url;
		String user = local_user;
		String password = local_password;
		con = DriverManager.getConnection(url, user, password);
		String fieldsStr="";
		for(int i=0;i<oldFields.size();i++){
			String flag = ",";
			if(i==oldFields.size()-1){
				flag="";
			}
			fieldsStr = fieldsStr+"t."+oldFields.get(i)+flag;
		}
		String sql = "select "+fieldsStr+" from "+tableName+" t "+wherePhrase+" "+orderPhrase;
		try {
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				Map<String, String> ob = new HashMap<String,String>();
				for(int i=0;i<oldFields.size();i++){
					String v = (result.getString(oldFields.get(i)));
					ob.put(oldFields.get(i), v);
				}
				result2.add(ob);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (result != null) {
				result.close();
			}
			if (pre != null) {
				pre.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return result2;
	}
	
	public List<Map<String, String>> getRowBySql(List<String> oldFields,String sql) throws Exception {
		List<Map<String, String>> result2 = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pre = null;
		ResultSet result = null;
		Class.forName(driverName);
		String url = local_url;
		String user = local_user;
		String password = local_password;
		con = DriverManager.getConnection(url, user, password);
		try {
			pre = con.prepareStatement(sql);
			result = pre.executeQuery();
			while (result.next()) {
				Map<String, String> ob = new HashMap<String,String>();
				for(int i=0;i<oldFields.size();i++){
					String v = (result.getString(oldFields.get(i)));
					ob.put(oldFields.get(i), v);
				}
				result2.add(ob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result != null) {
				result.close();
			}
			if (pre != null) {
				pre.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return result2;
	}
}
