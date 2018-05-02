package com.dijkstra.mail.controller.graph;

import static com.dijkstra.mail.useful.factory.Factory.simpleHash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class responsible to read the csv file and insert the data into a map variable.
 * Technology used to read the csv is commons-csv.
 * @author rafael.colombo
 *
 */
public class ManageFile {

    private static final Logger LOGGER = Logger.getLogger(ManageFile.class.getName());

    private ManageFile() {}

    /**
     * Process the file chosen and returns a map with the values.
     * @param filePath
     * @return a map object with the values who will be used to create the graph
     * @throws IOException
     */
    public static Map<String, Map<String, String>> processInput(String filePath,
            Map<String, Map<String, String>> upperMailNetwork) throws IOException {

        try (Stream<String> stream =  Files.lines(Paths.get(filePath))){
            List<List<String>> listOfLines = stream.map(lines -> Arrays.asList(lines.split(",")))
                    .collect(Collectors.toList());
            listOfLines.forEach(line -> iterateCSVLines(line, upperMailNetwork));

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        //Returns the map with all required informations to create the graph and calculate the best way to the destiny.
        return upperMailNetwork;

    }


    /**
     * Iterates the lines from the CSV file validating it.
     * @param line
     * @param upperMailNetwork
     */
    private static void iterateCSVLines(List<String> line, Map<String, Map<String, String>> upperMailNetwork){

        //Get the sender
        String upperKey = line.get(0);

        //Desconsider the lines starting with the @ and lines starting with numbers
        //Iterates the line starting from the first possible receiver
        if(! line.get(0).startsWith ( "@" ) && ! line.get ( 0 ).matches ( "^[0-9]")) {
            for (int i = 1; i < line.size(); i++) {

                //Splits the receiver String separating the receiver and the Hard to send a package to
                //him/her and inserts it into the innerMap.
                String[] item = line.get(i).split(":");

                upperMailNetwork.computeIfAbsent(upperKey, node -> simpleHash.get()).put(item[0], item[1]);
            }
        } else {
            //Considering that @ is the sender equals to ME, than it inserts the receiver and the package volume size.
            upperMailNetwork.computeIfAbsent(upperKey, node -> simpleHash.get()).put(line.get(1), line.get(2));
        }
    }
}