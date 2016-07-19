package com.whc.query;

import java.util.ArrayList;
import java.util.HashSet;

public class QueryNode {
	private String word;
	private ArrayList<Pair> pairs;
	private HashSet<Integer> docSet;
	private int num_doc;
	
	public QueryNode(){
		
	}
	
	public QueryNode(String word){
		this.word = word;
		this.docSet = new HashSet<Integer>();
		this.pairs = new ArrayList<Pair>();
	}
	
	public void ReSetPairs(){
		ArrayList<Pair> newPairs = new ArrayList<Pair>();
		for (Pair pr : this.pairs) {
			if(this.hasDoc(pr.getDocId())){
				newPairs.add(pr);
			}
		}
		setPairs(newPairs);
		
	}
    
	public boolean hasDoc(int docId){
		return this.docSet.contains(docId);
	}
	
	public void addNewDoc(int docId){
		this.docSet.add(docId);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	
	public ArrayList<Pair> getPairs() {
		return pairs;
	}

	public void setPairs(ArrayList<Pair> pairs) {
		this.pairs = pairs;
	}
		
	public HashSet<Integer> getDocSet() {
		return docSet;
	}

	public void setDocSet(HashSet<Integer> docSet) {
		this.docSet = docSet;
	}

	public int getNum_doc() {
		return num_doc;
	}

	public void setNum_doc() {
		this.num_doc = this.pairs.size();
	}
	
	
	
}
