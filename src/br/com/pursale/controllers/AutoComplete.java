package br.com.pursale.controllers;


import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Stateful
@LocalBean
@Model
public class AutoComplete {
	
	public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        
        Set<String> ordenedResults = new HashSet<String>();
        
        try{
        	String url = "http://api.geonames.org/search?username=pursale&lang=pt&country=br&charset=ISO-8859-1&name_startsWith="+URLEncoder.encode(query, "UTF-8");
        	
        	// Define the server endpoint to send the HTTP request to
		    URL serverUrl = new URL(url);
		    HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();
		    
		    // Reading from the HTTP response body
		    Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
		    String xml = "";
		    while(httpResponseScanner.hasNextLine()) {
		    	xml = xml + httpResponseScanner.nextLine();
		    }
		    
		    //System.out.println(xml);
		    
		    Document doc = loadXMLFromString(xml);
		    Element elem = doc.getDocumentElement();
            
            NodeList nl = elem.getElementsByTagName("name");  
            for( int i=0; i<nl.getLength(); i++ ) {  // percorre todos os atributos encontrados
            	Element tagClasse = (Element) nl.item(i);
            	String place = tagClasse.getTextContent();
            	
            	ordenedResults.add(place);
            }
		    
            for(String placeName : ordenedResults){
            	results.add(placeName);
            }
            
		    httpResponseScanner.close();
		}catch(Exception e){
		    	System.out.println(e);
		}
        
        return results;
    }
	
	public static Document loadXMLFromString(String xml) throws Exception{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
}
