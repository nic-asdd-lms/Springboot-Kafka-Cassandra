package org.igot.model;

import java.util.List;

public class ApiResultResponse {
	
	private int count;
	private List<ApiRespContent> content;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ApiRespContent> getContent() {
		return content;
	}

	public void setContent(List<ApiRespContent> content) {
		this.content = content;
	}

}
