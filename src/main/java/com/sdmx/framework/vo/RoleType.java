package com.sdmx.framework.vo;

public enum RoleType {
	SystemManger("系统管理员",1),
	DepartMember("部门员工",2),
	DepartManage("部门调度",3),
	ProductManage("生产部门管理员",4),
	TestCenterManage("封测中心管理员",5),
	Default("默认",6),
	MarketManger("市场人员",7);
	
	private String label;
	private int id;
	private RoleType(String label,int id){
		this.label=label; 
		this.id=id;
	}
	public String getLabel(){
		return this.label;
	}
	public int getId(){
		return this.id;
	}
	
	public static RoleType getType(String name) {
		if(name.equals("市场人员")){
	        return RoleType.DepartMember;
	    }
        for (RoleType c : RoleType.values()) {  
            if (name.equals(c.getLabel())) {  
            	return c;
            }  
        }
        if(name.equals("市场人员,部门员工")||name.equals("部门员工,市场人员")){
        	return RoleType.DepartMember;
        }
        if(name.equals("市场人员,部门调度")||name.equals("部门调度,市场人员")){
        	return RoleType.DepartManage;
        }
        return RoleType.Default;
    } 
}
