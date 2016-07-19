package com.whc.query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class WordQuery {
   public static final int Total_Doc_Num = 52139;
   private int Avg_length;
   private BufferedReader IndexOut;
   private String indexPath;
   private String mapIndex;
   private HashMap<Integer, DocMap> map;
   
   
   public WordQuery(String indexPath, String mapIndex) throws IOException{
	   this.indexPath = indexPath;
	   this.mapIndex = mapIndex;
	   this.map = new HashMap<Integer, DocMap>();
	   this.IndexOut = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.indexPath))));
   }
   
	public void find(String input, int ListNum, ArrayList<String> UrlResult) throws IOException {
		String[] words = input.split(" ");
		HashSet<String> QuerySet = new HashSet<String>();
		
		int getResultNum = 0;
		for (int i = 0; i < words.length; i++) {
			QuerySet.add(words[i]);
		}
		String line = null;
		ArrayList<QueryNode> results = new ArrayList<QueryNode>();
		while ((line = IndexOut.readLine()) != null&&getResultNum < QuerySet.size()) {
			String[] strs = line.split(" ");
			if (QuerySet.contains(strs[0])) {
				getResultNum++;
				QueryNode qd = new QueryNode(strs[0]);
				for (int i = 1; i < strs.length; i++) {
					String[] Id_Pos = strs[i].split(",");
					int docId = Integer.parseInt(Id_Pos[0]);
					if (qd.hasDoc(docId)) {
						for (Pair pr : qd.getPairs()) {
							if (pr.getDocId() == docId) {
								pr.setFrequence(1);
							}
						}
					} else {
						qd.getPairs().add(new Pair(docId, 1));
						qd.addNewDoc(docId);
					}
				}
				qd.setNum_doc();
				results.add(qd);
				
			}
		} 
		
		HashSet<Integer> newSet = getFinalDocSet(results);
		for (QueryNode qr : results) {
			qr.setDocSet(newSet);
			qr.ReSetPairs();
		}
		
		HashMap<Integer, ResultNode> resultMap = new HashMap<Integer, ResultNode>();
		getResultMap(results, resultMap);
		ListNum = resultMap.size()<ListNum?resultMap.size():ListNum;
		MyCompaator mc = new MyCompaator();
		PriorityQueue<ResultNode> resultQueue = new PriorityQueue<ResultNode>(ListNum,mc);
		for (Entry<Integer, ResultNode> entry : resultMap.entrySet()) {
			resultQueue.add(entry.getValue());
		}
		
		while(UrlResult.size()<ListNum){
			UrlResult.add(resultQueue.poll().getUrl());
		}
		
   }
	
	public HashSet<Integer> getFinalDocSet(ArrayList<QueryNode> results) {
		HashMap<Integer, Integer> records = new HashMap<Integer, Integer>();
		HashSet<Integer> finalHashSet = new HashSet<Integer>();
		for (QueryNode queryNode : results) {
			for (Pair pr : queryNode.getPairs()) {
				if (records.containsKey(pr.getDocId())) {
					int times = records.get(pr.getDocId()) + 1;
					records.put(pr.getDocId(), times);
				} else {
					records.put(pr.getDocId(), 1);
				}
			}
		}
		
		for (Entry<Integer, Integer> entry : records.entrySet()) {
			if (entry.getValue() == results.size()) {
				finalHashSet.add(entry.getKey());
			}

		}
		return finalHashSet;
	}
	
	public void getResultMap(ArrayList<QueryNode> qrs, HashMap<Integer, ResultNode> resultMap ){
		//ArrayList<String> results = new ArrayList<String>();
		for (QueryNode qr : qrs) {
			for (Pair pr : qr.getPairs()) {
				int docLength = this.map.get(pr.getDocId()).getLength();
				String url = this.map.get(pr.getDocId()).getUrl();
				pr.setK(docLength, this.Avg_length);
				pr.setScorce(qr.getNum_doc());
				if(resultMap.containsKey(pr.getDocId())){
					resultMap.get(pr.getDocId()).setTotal_score(pr.getScorce());
				}else{
					ResultNode result = new ResultNode(pr.getDocId(), pr.getScorce(), url);
					resultMap.put(pr.getDocId(), result);
				}
				
			}
		}
		
	}
	
	public void loadMap() throws IOException{
		File file = new File(this.mapIndex);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line =null;
		int sum_length= 0;
		while((line = br.readLine())!=null){
			String[] strs = line.split(" ");
			DocMap newElementDoc = new DocMap(Integer.parseInt(strs[0]), strs[1], Integer.parseInt(strs[2])); 
			this.map.put(newElementDoc.getDocId(), newElementDoc);
			sum_length += newElementDoc.getLength();
		}
		this.Avg_length = sum_length/Total_Doc_Num;
		br.close();
	}
	
	class MyCompaator implements Comparator<ResultNode>{

		@Override
		public int compare(ResultNode o1, ResultNode o2) {
			if(o1.getTotal_score()>o2.getTotal_score()){
				return 1;
			}
			return -1;
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		String indexPath = "/Users/whaochen/Documents/workspaceJavaEE/QueryEngine/src/com/whc/query/result/invertedIndex.txt";
		String mapIndex = "/Users/whaochen/Documents/workspaceJavaEE/QueryEngine/src/com/whc/query/result/map.txt";
		WordQuery wdQuery = new WordQuery(indexPath, mapIndex);
		wdQuery.loadMap();
		ArrayList<String> list = new ArrayList<String>();
		wdQuery.find("algorithm", 5, list);
		if(list.size()>0){
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		}else{
			System.out.println("not found");
		}
		 
	}
}