/**
 * 
 */
package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author COMP 8547 Team 3
 * This class reads text files splits word and stores into hash table.
 */
public class HashTableImpl {
	
	/**
	 * This method reads file and stores in hash table.
	 * @param hashTables
	 * @param inputDir
	 */
	public void scanTextFiles(Hashtable<String, Hashtable<String, List<Integer>>> hashTables,File inputDir) {
		for (File inFile : inputDir.listFiles()) {
			if (inFile.isFile()) {
				Hashtable<String, List<Integer>> hs = new Hashtable<String, List<Integer>>();
				String file = Preferences.TEXT_FILES_DIRECTORY + "/" + inFile.getName();
				insertFileInHashTable(file, hs);
				hashTables.put(file, hs);
			}
		}
	}
	
	/**
	 * This method calls other method to split words and adds it to hash table.
	 * @param inputfile
	 * @param hashtable
	 */
	public void insertFileInHashTable(String inputfile, Hashtable<String, List<Integer>> hashtable) {
		String[] wordsFromTextFile = lineToWords(inputfile);
		for (int i=0; i < wordsFromTextFile.length; i++) {
			addWordToHashTable(wordsFromTextFile[i], i, hashtable);
		}
	}

	/**
	 * This method inserts word and their indexes into hash table.
	 * @param word
	 * @param index
	 * @param hashtable
	 */
	public void addWordToHashTable(String word, int index, Hashtable<String, List<Integer>> hashtable) {
		List<Integer> indexes;
		if (hashtable.containsKey(word)) {
			indexes = hashtable.get(word);
		} else {
			indexes = new ArrayList<Integer>();
		}
		indexes.add(index);
		hashtable.put(word, indexes);
	}
	
	/**
	 * This method splits line to words.
	 * @param inputfile
	 * @return
	 */
	public String[] lineToWords(String inputfile) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(inputfile));
			StringBuilder sb = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());				
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			String fileContents = sb.toString();
			String[] words = splitStringLineInWords(fileContents);
			bufferedReader.close();
			return words;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method splits string line into words.
	 * @param fileContents
	 * @return
	 */
	public String[] splitStringLineInWords(String fileContents) {
		// TODO Auto-generated method stub
		fileContents = fileContents.toLowerCase();
		fileContents = fileContents.replaceAll("[^a-zA-Z0-9 ]", " ");
		fileContents = fileContents.replaceAll("\\s+", " ").replaceAll("^\\s+", "");
		String[] queryList = fileContents.split(" ");
		return queryList;
	}
}
