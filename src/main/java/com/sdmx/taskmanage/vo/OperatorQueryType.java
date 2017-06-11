package com.sdmx.taskmanage.vo;

public enum OperatorQueryType {
	SuspendingQuery("1","待处理查询"),
	AllQuery("2","全部查询");
	
	private OperatorQueryType(String value,String label){
		this.value = value;
		this.label = label;
	}
	private final String value;
	private final String label;
	public String getValue(){return this.value;}
	public String getLabel(){return this.label;}
}
