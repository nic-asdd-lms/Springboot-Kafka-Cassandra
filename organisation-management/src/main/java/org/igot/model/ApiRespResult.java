package org.igot.model;

import java.util.List;
import java.util.Map;

public class ApiRespResult {
	private ApiResultResponse response;
	private ApiHierarchyResultContent content;
	private ApiHierarchyResultBatch batch;
	private Map<String, Object> questionSet;
	private List<Map<String, Object>> questions;

	public ApiResultResponse getResponse() {
		return response;
	}

	public void setResponse(ApiResultResponse response) {
		this.response = response;
	}

	public ApiHierarchyResultContent getContent() {
		return content;
	}

	public void setContent(ApiHierarchyResultContent content) {
		this.content = content;
	}
	public ApiHierarchyResultBatch getBatch() {
		return batch;
	}

	public void setBatch(ApiHierarchyResultBatch batch) {
		this.batch = batch;
	}

	public Map<String, Object> getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(Map<String, Object> questionSet) {
		this.questionSet = questionSet;
	}

	public List<Map<String, Object>> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Map<String, Object>> questions) {
		this.questions = questions;
	}
}

