package com.dijkstra.mail.controller.graph;

import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.model.impl.WeightedNode;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Class responsible to manage the creation of the graph and calculate the volumetric weight.
 * Technologies used are Hipster4j. It was necessary to customize the return when there is no way to the destiny. 
 * This techonology is responsible to create the graph and find the best way to the destiny.
 * @author rafael.colombo
 *
 */
public class ManageGraph {

	
	/**
	 * Returns the graph created based on the map passed as a parameter
	 * @param mailNetwork
	 * @return the graph created.
	 */
	public static GraphBuilder<String,Integer> generateGraph(Map<String, Map<String, String>> mailNetwork){
		
		//Creates a GraphBuilder object from Hipster4j
		GraphBuilder<String,Integer> graph = GraphBuilder.create();
		
		//Iterates the Map to populate the graph
		mailNetwork.entrySet().forEach(entry -> mountGraph(entry, graph));
		
		//return the graph created
		return graph;
	}
	
	
	
	
	/**
	 * Function responsible to create the graph based on a Hashmap and a GraphBuilder from Hipster4j
	 * @param entry
	 * @param graph
	 */
	private static void mountGraph(Map.Entry<String, Map<String,String>> entry, GraphBuilder<String,Integer> graph){
		
		//Grab the first object at the specific line
		String from = entry.getKey();
		
		//Verify if the object doesn`t start with @
		if(!from.startsWith("CSVRecord") && !from.startsWith("@")){
			
			//Gets the sub Hashmap
			Map<String, String> subMap = entry.getValue();
			
			//Iterates the sub Hashmap
			subMap.entrySet().forEach(subEntry -> iterateSubMap(subEntry, graph, from));
			
		}
	}
		
	
	
	/**
	 * Iterates a map to create the graph and creates the path from / to with their hard cost. Helps the mountGraph function
	 * @param subEntry
	 * @param graph
	 * @param from
	 */
	private static void iterateSubMap(Map.Entry<String, String> subEntry, GraphBuilder<String,Integer> graph, String from){
		String to = subEntry.getKey();					
		Integer hard = Integer.parseInt(subEntry.getValue());
		graph.connect(from).to(to).withEdge(hard);	
	}
	
	
	
	
	/**
	 * Calculate the volumetric weight based on params
	 * @param length
	 * @param width
	 * @param height
	 * @return the volumetric weight rounded up to the nearest 0.5kg.
	 */
	private static Double calcVolumetricWeight(double length, double width, double height){
		int weightDivisor = 5000;
		Double roundFactor = 0.5;
		
		Double originalVolume = length * width * height;

		return roundFactor * Math.ceil(originalVolume / weightDivisor / roundFactor);
		 
		
	}
	
	/**
	 * Responsible for calculate the final cost based on the last node.
	 * @param node
	 * @param pathIterator
	 * @return the final cost
	 */
	public static String calculateCost(WeightedNode node, Map.Entry<String, String> pathIterator){
		
		//Gets the dimension of the package
		String packageDimension[] = pathIterator.getValue().split("x");
			
		//Gets the volumetric weight
		double roundFactor = ManageGraph.calcVolumetricWeight(Double.parseDouble(packageDimension[0]), Double.parseDouble(packageDimension[1]), Double.parseDouble(packageDimension[2]));
			
		//Gets hard sqrt 
		double sqrtHard = Math.sqrt((double) node.getCost());
			
		//Calculate the cost and formats it with 2 decimal places.
		return new DecimalFormat("#.00").format(sqrtHard * roundFactor);
	}
	
}
