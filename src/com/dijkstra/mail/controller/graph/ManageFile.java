package com.dijkstra.mail.controller.graph;

import com.dijkstra.mail.useful.factory.Factory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * Class responsible to read the csv file and insert the data into a map variable.
 * Technology used to read the csv is commons-csv.
 * @author rafael.colombo
 *
 */
public class ManageFile {

	
	/**
	 * Process the file chosen and returns a map with the values.
	 * @param filePath
	 * @return a map object with the values who will be used to create the graph
	 * @throws IOException
	 */
	public static Map<String, Map<String, String>> processInput(String filePath, JSONObject inputContent, Map<String, Map<String, String>> upperMailNetwork) throws IOException {
		Reader in = null;
		
		if(inputContent != null){
			String content = (String) inputContent.get("fileContent");
			//Creates the input reader based on the file path
			in = new StringReader(content);
		} else {
			in = new FileReader(filePath);
		}
				
		//Creates an interator based on input reader
		CSVFormat.EXCEL.parse(in).forEach(line -> iterateCSVLines(line, upperMailNetwork));

		//Returns the map with all required informations to create the graph and calculate the best way to the destiny.
		return upperMailNetwork;
		
	}
		
	
	/**
	 * Iterates the lines from the CSV file validating it.
	 * @param line
	 * @param upperMailNetwork
	 */
	private static void iterateCSVLines(CSVRecord line, Map<String, Map<String, String>> upperMailNetwork){
		
		//Get the sender
		String upperKey = line.get(0);
			
		
		//Desconsider the lines starting with the @ and lines starting with numbers
		//Iterates the line starting from the first possible receiver
		if(! line.get(0).startsWith ( "@" ) && ! line.get ( 0 ).matches ( "^[0-9]"))
			for (int i = 1; i < line.size(); i++) {

				//Splits the receiver String separating the receiver and the Hard to send a package to him/her and inserts it into the innerMap.
				String item[] = line.get(i).split(":");

				upperMailNetwork.computeIfAbsent(upperKey, node -> Factory.SIMPLEHASH.get()).put(item[0], item[1]);
			}
		else {
	    	//Considering that @ is the sender equals to ME, than it inserts the receiver and the package volume size.
	    	upperMailNetwork.computeIfAbsent(upperKey, node -> Factory.SIMPLEHASH.get()).put(line.get(1), line.get(2));
	    }
	}
}
