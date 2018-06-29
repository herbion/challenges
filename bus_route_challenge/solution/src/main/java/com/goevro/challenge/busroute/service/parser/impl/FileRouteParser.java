package com.goevro.challenge.busroute.service.parser.impl;


import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.service.parser.FileResourceRouteParser;
import com.goevro.challenge.busroute.service.parser.exception.IllegalDataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Loader of route collection from input file
 */
@Service
public class FileRouteParser implements FileResourceRouteParser {
  private static final Logger logger = LoggerFactory.getLogger(FileRouteParser.class);

  private final LineRouteParser lineParser;

  @Autowired
  public FileRouteParser(LineRouteParser lineParser) {
    this.lineParser = lineParser;
  }

  /**
   * Read routes file and return collection of parsed routes
   *
   * @param source Routes file
   * @return Collection of routes
   * @throws RuntimeException           failed to read routes file
   * @throws IllegalDataFormatException failed to parse file according to routes formant
   */
  @Override
  public Collection<Route> parse(File source) {
    logger.info("Started loading routes from {} resource", source);

    List<Route> routes;

    try (BufferedReader reader = Files.newBufferedReader(source.toPath())) {
      int itemsRemaining = parseRouteHeader(reader);

      logger.info("Estimated route collection size {}", itemsRemaining);

      routes = new ArrayList<>(itemsRemaining);

      while (itemsRemaining > 0) {
        String line = reader.readLine();
        routes.add(lineParser.parse(line));
        itemsRemaining--;
      }

    } catch (IOException e) {
      logger.error("Failed to load data file {}", source, e);
      throw new RuntimeException(e);
    } catch (Exception e) {
      logger.error("Failed to parse routes from {}", source, e);
      throw new IllegalDataFormatException(source, e);
    }

    logger.info("Successfully loaded {} routes from resource {}", routes.size(), source);
    return routes;
  }

  private int parseRouteHeader(BufferedReader reader) throws IOException {
    return Integer.parseInt(reader.readLine());
  }

}
