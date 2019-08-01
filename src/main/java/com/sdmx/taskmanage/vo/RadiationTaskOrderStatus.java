package com.sdmx.taskmanage.vo;

public enum RadiationTaskOrderStatus {
	//SAVE_DEPARTMEMBER(11,"部门员工保存"),
	//NOTPASS_DEPARTMANAGE(12,"部门调度不通过 "),
	//WAITTOCONFIRM_DEPARTMANAGE(21,"部门调度待审核"),
	NOTPASS_PRODUCTMANAGE(22,"调度审核不通过"),
	//NOTPASS_TESTCENTERMANAGE(23,"封测审核不通过"),
	NOTPASS_KANGJIAMANAGE(24,"试验组审核不通过"),
	WAITTOCONFIRM_PRODUCTMANAGE(31,"调度长审核"),
	//WAITTOCONFIRM_TESTCENTERMANAGE(41,"封测中心待审核"),
	//WAITTOCHARGE_TESTCENTERMANAGE(42,"封测中心待核价"),
	//WAITTOFIX_TESTCENTERMANAGE(43,"封测中心待确认"),
	WAITTOCONFIRM_KANGJIAMANAGE(61,"试验组审核"),
	WAITTOFIX_KANGJIAMANAGE(62,"试验进行中"),
	//WAITTOFIX_DEPARTMANAGE(13,"部门待确认"),
	COMPLETED(51,"试验完成");
	private RadiationTaskOrderStatus(int value,String label){this.value=value;this.label=label;}
	private final int value;
	private final String label;
	public int getValue(){return this.value;}
	public String getLabel(){return this.label;}
}
