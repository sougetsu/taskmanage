package com.sdmx.framework.vo;

public class JsonResult {

	private boolean success = false;

	private String msg = "";

	private Object obj = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	public static JsonResult success(String msg,Object obj){
		JsonResult json = new JsonResult();
		json.setSuccess(true);
		json.setMsg(msg);
		json.setObj(obj);
		return json;
	}
	public static JsonResult failed(String msg,Object obj){
		JsonResult json = new JsonResult();
		json.setSuccess(false);
		json.setMsg(msg);
		json.setObj(obj);
		return json;
	}
}
