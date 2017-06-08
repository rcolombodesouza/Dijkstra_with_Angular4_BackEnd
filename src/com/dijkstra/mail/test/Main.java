package com.dijkstra.mail.test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static com.dijkstra.mail.controller.SearchService.runDijkstraPost;
import static java.nio.file.Files.walk;
import static java.nio.file.Paths.get;

class Main {

	public static void main(String[] args) {
		
		//Read a specific folder and iterates all the files inside of it.
		try(Stream<Path> paths = walk(get("/tmp/allpago"))) {
			paths.forEach(filePath -> runDijkstraPost(filePath, null, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
