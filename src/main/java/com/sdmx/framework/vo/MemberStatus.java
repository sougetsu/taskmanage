package com.sdmx.framework.vo;

public enum MemberStatus {
	Normal(1,"正常"),
	Deleted(0,"已删除");
	
	private MemberStatus(int value,String label){
		this.value = value;
		this.label = label;
	}
	private final int value;
	private final String label;
	public int getValue(){return this.value;}
	public String getLabel(){return this.label;}
	
	public static String getStatusByKey(int value){
		for (MemberStatus c : MemberStatus.values()) {  
            if (value == c.getValue()) {  
            	return c.getLabel();
            }  
        }
		return null;
	}
}
