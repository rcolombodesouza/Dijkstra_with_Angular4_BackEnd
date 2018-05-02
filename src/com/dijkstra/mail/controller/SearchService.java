package com.dijkstra.mail.controller;

import static com.dijkstra.mail.useful.factory.Factory.complexHash;
import static com.dijkstra.mail.useful.factory.Factory.simpleHash;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Level;
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
            /* Verify if the files are regular and ends with .csv */
            if (filePath.toFile().isFile() &&  filePath.toString().endsWith(".csv")) {
                Map<String, Map<String, String>> mailNetwork = complexHash.get();
                mailNetwork.putAll(fileContent != null ? ManageFile.processInput(null, complexHash.get())
                        : ManageFile.processInput(filePath.toString(), complexHash.get()));
                generateGraph(mailNetwork, object, filePath);
            }
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
        Map<String, String> innerPathNetwork = mailNetwork.get("@");

        //Since @ is equal to ME, creates the SearchProblem from Hipster4j
        SearchProblem<Integer, String, WeightedNode<Integer, String, Double>> p = GraphSearchProblem
                .startingFrom("ME").in(graph.createDirectedGraph()).takeCostsFromEdges().build();

        //Iterate all the lines starting with @ to get the best way for each one of them.
        innerPathNetwork.entrySet().forEach(pathIterator -> iterateSearchesPost(pathIterator, p, object, filePath));

    }



    /**
     * Responsible for iterating the lines started with @
     * @param pathIterator
     * @param p
     */
    private static void iterateSearchesPost(Map.Entry<String, String> pathIterator, SearchProblem<Integer,
            String, WeightedNode<Integer, String, Double>> p, JSONObject object, Path filePath){

        Map<String, String> map = simpleHash.get();

        //Gets node`s best way from ME to destiny (pathIterator.getKey())
        WeightedNode<Integer, String, Double> node = Hipster.createDijkstra(p)
                .search(pathIterator.getKey()).getGoalNode();

        if(pathIterator.getKey().equalsIgnoreCase(node.state())) {

            //Calculate the cost and formats it with 2 decimal places.
            String finalCost = ManageGraph.calculateCost(node, pathIterator);

            if(object == null){
                LOGGER.log(Level.INFO, "The cost to send a package to {0} is {1} Euros.",
                        new Object[] {node.state(), finalCost});
            } else {
                if(filePath != null){
                    map.put("file", filePath.toString().substring(filePath.toString().lastIndexOf('\\') + 1));
                }
                map.put("source", "ME");
                map.put("target", node.state());
                map.put("cost",finalCost);
            }
        } else {
            if(object == null){
                LOGGER.log(Level.INFO, "There cost to send a package to {0} is infinity.",
                        new Object[] {pathIterator.getKey()});
            } else {
                if(filePath != null){
                    map.put("file", filePath.toString().substring(filePath.toString().lastIndexOf('\\') + 1));
                }

                map.put("source", "ME");
                map.put("target", pathIterator.getKey());
                map.put("cost","~");
            }
        }
        if(object != null){
            object.append("result", map);
        }
    }
}