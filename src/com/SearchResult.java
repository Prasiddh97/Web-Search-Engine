/**
 * 
 */
package com;

/**
 * @author COMP 8547 Team 3
 * This class stores search results
 *
 */
public class SearchResult {

	String filename;
	int frequency;
	float pageRank;
	
	/**
	 * This is parameterized constructor which assigns values to the variables.
	 * @param filename
	 * @param pageRank
	 */
	public SearchResult(String filename, float pageRank) {
		this.filename = filename;
		this.frequency = (int) pageRank;
		this.pageRank = pageRank;
	}
	
	
	
}
