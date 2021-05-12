/**
 * 
 */
package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/**
 * @author COMP 8547 Team 3
 * This is the main class from which calls methods from different classes to interect with user.
 *
 */
public class WSEMain {

	/**
	 * @param args
	 */
	
	Hashtable<String, Hashtable<String, List<Integer>>> hashTables;
	static HashTableImpl hs;
	public static ArrayList<String> linkList;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * Fetching URLs and saving it to ArrayList
		 */
		long start = System.currentTimeMillis();
		WSEMain searchengine = new WSEMain();
		
		searchengine.hashTables = new Hashtable<>();
		File inputDir = new File(Preferences.TEXT_FILES_DIRECTORY);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		hs = new HashTableImpl();
		System.out.println("+---------------------------------------------+");
		System.out.println("|        Web Search Engine by Team 3          |");
		System.out.println("+---------------------------------------------+");
		System.out.println("|        Julee \t\tChanchad              |");
		System.out.println("|        Nikhil \tKothari               |");
		System.out.println("|        Prasiddha \tPatel                 |");
		System.out.println("|        Roshan \tJames                 |");
		System.out.println("+---------------------------------------------+");
		System.out.println("|        Guided By: Dr. Luis Rueda            |");
		System.out.println("+---------------------------------------------+");
		System.out.println("Fetching urls from "+Preferences.DOMAIN_TO_SEARCH+"...");
		WebCrawler crawler = new WebCrawler(Preferences.CRAWLER_DEPTH);
		linkList = crawler.getPageLinks(Preferences.DOMAIN_TO_SEARCH,0);
					
		/**
		 * Converting weppages to text
		 */
		System.out.println("Parsing HTML pages...");
		//Converting HTML pages to Text Files..
		HTMLToText.convertHTMLtoText(linkList);
		
		System.out.println("Spliting text file into tokens and storing...");
		//Splitting text files to words and insert into hash table
		WSEMain.hs.scanTextFiles(searchengine.hashTables,inputDir);
		String serachLine = "";
		//Searching word
		while (!serachLine.equals("no")) {
			
			do {
				System.out.println("\nPlease enter word to search ('no' to exit)");
				try {
					Search search = new Search();
					serachLine = bufferedReader.readLine();
					if(serachLine.equals("no")) {
						System.out.println("Thank you for using Team 3 Search Engine..");
						break;
					}
					//searching word
					search.search(serachLine, searchengine.hashTables);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (serachLine.isEmpty() || serachLine.trim().length()==0);
		}
		
	}

}
