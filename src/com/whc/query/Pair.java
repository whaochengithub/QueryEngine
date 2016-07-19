package com.whc.query;


public class Pair{
	private static final int Total_Doc_Num = 52139;
	private int docId;
	private int frequence;
	private double scorce;
	private double K;
	
	
	public Pair(int docId, int frequence){
		
		this.docId = docId;
		this.frequence = frequence;
	}
	
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public int getFrequence() {
		return frequence;
	}
	public void setFrequence(int frequence) {
		this.frequence += frequence;
	}
	
	public double getScorce() {
		return scorce;
	}

	public void setScorce(int Num_doc) {
		this.scorce = Math.log((Total_Doc_Num-Num_doc+0.5)/(Num_doc+0.5))+(this.frequence*(1+1.2)/this.K+frequence);
	}

	public double getK() {
		return K;
	}
	
	public void setK(int Avg_length, int docLength) {
		this.K = 1.2*((1-0.75)+(docLength/Avg_length));
	}
	
	
	
	
	
}
