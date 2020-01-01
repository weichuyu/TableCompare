package com.yu.tools.poi;

public class DualData implements Comparable {
	public String dataName;
	public String serviceName;
	public String headerName;
	public String width = "15";
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataName == null) ? 0 : dataName.hashCode());
		result = prime * result
				+ ((serviceName == null) ? 0 : serviceName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DualData other = (DualData) obj;
		if (dataName == null) {
			if (other.dataName != null)
				return false;
		} else if (!dataName.equals(other.dataName))
			return false;
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName))
			return false;
		return true;
	}
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return (this.serviceName).compareTo(((DualData)o).serviceName);
	}
	
}
