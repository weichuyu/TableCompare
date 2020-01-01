package com.yu.tools.dao;

public class ModifiedDataRow {
	private String keyJson;
	private String field;
	private String oldValue;
	private String changeIcon = "=>";
	private String newValue;
	public String getKeyJson() {
		return keyJson;
	}
	public void setKeyJson(String keyJson) {
		this.keyJson = keyJson;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getChangeIcon() {
		return changeIcon;
	}
	public void setChangeIcon(String changeIcon) {
		this.changeIcon = changeIcon;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
}
