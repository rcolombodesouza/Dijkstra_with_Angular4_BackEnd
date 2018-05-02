package com.dijkstra.mail.test;

import static com.dijkstra.mail.controller.SearchService.runDijkstraPost;
import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.dijkstra.mail.controller.SearchService;

class Main {
    static final Logger LOGGER = Logger.getLogger(SearchService.class.getName());
    public static void main(String[] args) {

        //Read a specific folder and iterates all the files inside of it.
        try(Stream<Path> paths = walk(get("/tmp/allpago"))) {
            paths.forEach(filePath -> runDijkstraPost(filePath, null, null));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
