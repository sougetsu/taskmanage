package com.sdmx.ersaitask.vo;

public enum ErsaiTaskOrderStatus {
	//市场二部创建
	SAVE_MARKET(21,"市场二部保存"),
	NOTPASS_PRODUCTMANAGE(22,"生产部门审核不通过"),
	NOTPASS_TESTCENTERMANAGE(23,"封测审核不通过"),
	WAITTOFIX_DEPARTMANAGE(24,"市场二部待确认"),
	WAITTOCONFIRM_PRODUCTMANAGE(31,"生产部门待审核"),
	WAITTOCONFIRM_TESTCENTERMANAGE(41,"封测中心待审核"),
	WAITTOCHARGE_TESTCENTERMANAGE(42,"封测中心待核价"),
	WAITTOFIX_TESTCENTERMANAGE(43,"封测中心待确认"),
	COMPLETED(51,"完成"),
	COMPLETEDTOYANS(52,"完成转验收"),
	CANCELED(99,"取消");
	private ErsaiTaskOrderStatus(int value,String label){this.value=value;this.label=label;}
	private final int value;
	private final String label;
	public int getValue(){return this.value;}
	public String getLabel(){return this.label;}

}
