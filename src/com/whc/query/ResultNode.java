package com.whc.query;

public class ResultNode {
	private int docId;
	private double total_score;
	private String url;
	
	public ResultNode(int docId, double total_scorce, String url){
		this.docId = docId;
		this.total_score = total_scorce;
		this.url = url;
	}
	
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public double getTotal_score() {
		return total_score;
	}
	public void setTotal_score(double input_score) {
		this.total_score += input_score;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
