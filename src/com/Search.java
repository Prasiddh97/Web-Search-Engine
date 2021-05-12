/**
 * 
 */
package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import editdistance.Sequences;



/**
 * @author COMP 8547 Team 3
 * This class searches for the word in the hash table and if word not found then it suggests similar words.
 */
public class Search {
	
	HashTableImpl hashTableImpl = new HashTableImpl();
	
	/**
	 * This method searches for the word in hash table.
	 * @param serachLine
	 * @param hashTables
	 */
	public void search(String serachLine,Hashtable<String, Hashtable<String, List<Integer>>> hashTables) {
		
		String[] words = hashTableImpl.splitStringLineInWords(serachLine);		
		ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
		Enumeration<String> filenames = hashTables.keys();
		
		while (filenames.hasMoreElements()) {
			String filename = (String) filenames.nextElement();
			float pageRank = searchInSubHashTable(hashTables.get(filename), words);
			if (pageRank>0) {
				SearchResult searchResult = new SearchResult(filename, pageRank);
				searchResults.add(searchResult);
			}	
		}
		
		int total = searchResults.size();
		//Sorting search results
		Collections.sort(searchResults, new Comparator<SearchResult>() {
			public int compare(SearchResult a, SearchResult b)  {
				return (int) (b.pageRank - a.pageRank);
			}
		});
		
		//Displaying result into table format
		TableGenerator tableGenerator = new TableGenerator();
		tableGenerator.setHeaders("No.","URL","Word Occurances");
		for( int i=0; i < Math.min(total, Preferences.NUMBER_OF_LINKS_TO_DISPLAY); i++) {
			if((int)searchResults.get(i).frequency > 0)
			{
				String link =  (searchResults.get(i).filename.replace(Preferences.TEXT_FILES_DIRECTORY + "/", ""));
				//System.out.println("link1" +link);
				link = link.substring(0, link.length() - 4);
				link = WSEMain.linkList.get(Integer.parseInt(link));
				//System.out.println("link2" +link); 
				tableGenerator.addRow(Integer.toString(i+1),link,Integer.toString(searchResults.get(i).frequency));
			}
		}
		if (total>0)		tableGenerator.print();
		
		//if word is not available in parsed data then suggest similar words.
		if(total==0) {
			System.out.println("Word not found...");
			ArrayList<String> suggestions = searchSimilar(hashTables, words);
			if(suggestions.size()>0) {
				System.out.println("\nDid you mean?");
				for (int i = 0; i < suggestions.size(); i++) {
					System.out.println((i+1)+". "+suggestions.get(i));
				}
			}
		}
	}
	
	/**
	 * This method searches for similar words using Edit Distance
	 * @param hashTables
	 * @param words
	 * @return
	 */
	public ArrayList<String> searchSimilar(Hashtable<String, Hashtable<String, List<Integer>>> hashTables, String[] words) {
		ArrayList<String> arrayList = new ArrayList<String>();
		Enumeration<String> filenames = hashTables.keys();
				
		while (filenames.hasMoreElements()) {
			
			String filename = (String) filenames.nextElement();
			for (String query : words)
			{
				query = query.toLowerCase();
				Enumeration<String> keys = hashTables.get(filename).keys();
				while(keys.hasMoreElements())
				{
					String word1 = keys.nextElement();
					int distance = Sequences.editDistance(word1, query);
					
					String trimmWord = null;
					if(word1.length() >= query.length())
						trimmWord = word1.substring(0, query.length());
					
//					System.out.println(word1+"- "+query + "= "+distance);
					if(distance>0 && distance<=3 && query.equals(trimmWord))
						if(!arrayList.contains(word1))
							arrayList.add(word1);
				}
			}
						
		}
		return arrayList;
	}
	
	/**
	 * This method searches in Sub Hash Table for specified word.
	 * @param subHashTable
	 * @param words
	 * @return
	 */
	public float searchInSubHashTable(Hashtable<String, List<Integer>> subHashTable, String[] words) {
		//calling edit distance function
		
		float pagerank = 0.0f;
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		for (String query : words) {
			query = query.toLowerCase();
			if (subHashTable.containsKey(query)) {
				List<Integer> indexes = subHashTable.get(query);
				results.add(indexes);
			} else {
				results.add(new ArrayList<Integer>());
			}
		}
		pagerank = pageRank(results);
		return pagerank;
		
	}

	/**
	 * This method gives rank to the pages based on occurances of the wordd.
	 * @param results
	 * @return
	 */
	public float pageRank(List<List<Integer>> results) {
		if (results.isEmpty()) {
			return 0.0f;
		}

		if (results.size() == 1) {
			return results.get(0).size();
		}

		float pageRank= 0.0f;
		Boolean flag = false;
		List<Integer> firstQueryIndexList = results.get(0);

		for (int j=0; j < results.size(); j++) {
			if(results.get(j).size()>0) pageRank++;
		}
		
		for (int i =0; i < firstQueryIndexList.size(); i++) {
			flag = true;
			for (int j=1; j < results.size(); j++) {
				int numberSearch = firstQueryIndexList.get(i) + j;
				if (!results.get(j).contains(numberSearch)) {
					flag = false;
					break;
				}
			}
			if (flag == true) {
				pageRank += 100;
			}
		}
		return pageRank;
	}
	
	
	
	}
