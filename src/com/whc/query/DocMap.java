package com.whc.query;

public class DocMap {
    private int docId;
    private String url;
    private int length;
    
    public DocMap(int docId, String url, int length){
    	this.docId = docId;
    	this.url = url;
    	this.length = length;
    }
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
    
    
}
