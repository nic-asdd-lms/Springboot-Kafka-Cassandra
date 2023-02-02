package org.igot.model;

public class ApiResp {
	private String id;
	private String ver;
	private String ts;
	private ApiRespParam params;
	private String responseCode;
	private ApiRespResult result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public ApiRespParam getParams() {
		return params;
	}
	public void setParams(ApiRespParam params) {
		this.params = params;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public ApiRespResult getResult() {
		return result;
	}
	public void setResult(ApiRespResult result) {
		this.result = result;
	}
}
