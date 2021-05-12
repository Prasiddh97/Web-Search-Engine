/**
 * 
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author COMP 8547 Team 3
 * This class converts HTML pages to text files.
 *
 */
public class HTMLToText {
	
	/**
	 * This method converts HTML pages to Text files.
	 * @param list
	 */
	public static void convertHTMLtoText(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			Document doc;
			try {
				doc = Jsoup.connect(list.get(i)).get();
				String textFile = doc.text();
				PrintWriter writer = new PrintWriter(Preferences.TEXT_FILES_DIRECTORY+"/"+i+".txt");
				writer.println(textFile);
				writer.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	}
}
