package com.dijkstra.mail.controller;

import static com.dijkstra.mail.useful.factory.Factory.complexHash;
import static com.dijkstra.mail.useful.factory.Factory.simpleHash;
import static com.dijkstra.mail.useful.constants.Constants.FILE_FORMAT;
import static com.dijkstra.mail.useful.constants.Constants.FIRST_ELEMENT;
import static com.dijkstra.mail.useful.constants.Constants.MESSAGE;
import static com.dijkstra.mail.useful.constants.Constants.FILE;
import static com.dijkstra.mail.useful.constants.Constants.RESULT;
import static com.dijkstra.mail.useful.constants.Constants.TARGET;
import static com.dijkstra.mail.useful.constants.Constants.COST;
import static com.dijkstra.mail.useful.constants.Constants.SOURCE;
import static com.dijkstra.mail.useful.constants.Constants.INFINITY_MESSAGE;
import static com.dijkstra.mail.useful.constants.Constants.INFINITY_SIMBOL;
import static com.dijkstra.mail.useful.constants.Constants.PROBLEM_STARTING_POINT;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.INFO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONObject;

import com.dijkstra.mail.controller.graph.ManageFile;
import com.dijkstra.mail.controller.graph.ManageGraph;

import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.SearchProblem;

/**
 * Class responsible to receive the files and run the searches.
 * @author rafael.colombo
 *
 */
public class SearchService {

    private static final Logger LOGGER = Logger.getLogger(SearchService.class.getName());
        
    private SearchService() {}

    
    /**
     * Receives the filePath and object from the frontEnd, verify if it`s a csv file and runs the search.
     * @param filePath
     * @param object
     */
    public static void runDijkstraPost(Path filePath, JSONObject fileContent, JSONObject object){
        try {
            /* Verify if the files are regular and ends with .CSV */
            if (filePath.toFile().isFile() &&  filePath.toString().endsWith(FILE_FORMAT)) {
                Map<String, Map<String, String>> mailNetwork = complexHash.get();
                mailNetwork.putAll(fileContent != null ? ManageFile.processInput(null, complexHash.get())
                        : ManageFile.processInput(filePath.toString(), complexHash.get()));
                generateGraph(mailNetwork, object, filePath);
            }
        } catch (IOException | NullPointerException e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
        }
    }


    /**
     * Generates the Graph
     * @param mailNetwork
     * @param object
     * @param filePath
     */
    private static void generateGraph(Map<String, Map<String, String>> mailNetwork, JSONObject object, Path filePath){
        //Generate the graph based on the map created
        GraphBuilder<String,Integer> graph = ManageGraph.generateGraph(mailNetwork);

        // Read only the lines starting with @
        Map<String, String> innerPathNetwork = mailNetwork.get(PROBLEM_STARTING_POINT);

        //Since @ is equal to ME, creates the SearchProblem from Hipster4j
        SearchProblem<Integer, String, WeightedNode<Integer, String, Double>> searchProblem = GraphSearchProblem
                .startingFrom(FIRST_ELEMENT).in(graph.createDirectedGraph()).takeCostsFromEdges().build();

        //Iterate all the lines starting with @ to get the best way for each one of them.
        innerPathNetwork.entrySet().forEach(pathIterator -> iterateSearchesPost(pathIterator, searchProblem, object,
        		filePath));
    }


    /**
     * Responsible for iterating the lines started with @
     * @param pathIterator
     * @param p
     */
    private static void iterateSearchesPost(Map.Entry<String, String> pathIterator, SearchProblem<Integer,
            String, WeightedNode<Integer, String, Double>> p, JSONObject object, Path filePath){
        Map<String, String> map;

        //Gets node`s best way from ME to destiny (pathIterator.getKey())
        WeightedNode<Integer, String, Double> node = Hipster.createDijkstra(p)
                .search(pathIterator.getKey()).getGoalNode();

        if(pathIterator.getKey().equalsIgnoreCase(node.state())) {
            //Calculate the cost and formats it with 2 decimal places.
            String finalCost = ManageGraph.calculateCost(node, pathIterator);
            map = updateMap(object, node, filePath, finalCost, MESSAGE, new Object[] {node.state(), finalCost});
        } else {
            map = updateMap(object, node, filePath, INFINITY_SIMBOL, INFINITY_MESSAGE, 
            		new Object[] {pathIterator.getKey()});
        }
        if(object != null){
            object.append(RESULT, map);
        }
    }

    
    /**
     * Update the map according to object
     * @param object to be used for update
     * @param node target state
     * @param filePath represent the .CSV file path
     * @param finalCost cost from source to target
     * @param msg to be shown
     * @param msgObject parameters to be imported into the message
     * @return the updated map
     */
    private static Map<String, String> updateMap(JSONObject object, WeightedNode<Integer, String, Double> node,
            Path filePath, String finalCost, String msg, Object[] msgObject) {
        Map<String, String> map = simpleHash.get();
        if(object == null){
            LOGGER.log(INFO, msg, msgObject);
        } else {
            if(filePath != null){
                map.put(FILE, filePath.toString().substring(filePath.toString().lastIndexOf('\\') + 1));
            }
            map.put(SOURCE, FIRST_ELEMENT);
            map.put(TARGET, node.state());
            map.put(COST,finalCost);
        }
        return map;
    }
}