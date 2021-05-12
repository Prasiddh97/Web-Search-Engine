/**
 * 
 */
package com;

import java.util.ArrayList;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author COMP 8547 Team 3
 * This class uses Jsoup library and crawls URL 
 *
 */
public class WebCrawler {

	/**
	 * @param args
	 */
	private static int max_depth;
	int count = 0;
	ArrayList<String> linkList;
	HashSet<String> set;
	
	 public WebCrawler(int depth) {
	        max_depth = depth;
	        linkList = new ArrayList<String>();
	        set = new HashSet<String>();
	    }
	
	 /**
	  * This method fetches URL and stores into ArrayList
	  * @param URL
	  * @param depth
	  * @return
	  */
	public ArrayList<String> getPageLinks(String URL, int depth) {
        if ((!set.contains(URL) && (depth < max_depth))) {
        	set.add(URL);
        	linkList.add(count, URL); 
        	count++;     	
            try {
            	
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (Exception e) {
            	//log file implementation
            }
        }
		return linkList;
    }

   

}
