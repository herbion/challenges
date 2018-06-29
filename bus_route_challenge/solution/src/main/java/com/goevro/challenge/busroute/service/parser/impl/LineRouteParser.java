package com.goevro.challenge.busroute.service.parser.impl;

import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.service.parser.StringRouteParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * Parse stations from single route
 */
@Service
public class LineRouteParser implements StringRouteParser {

  private static final Logger logger = LoggerFactory.getLogger(LineRouteParser.class);

  /**
   * Parse stations from string representation of route.
   * Expected format:
   *   <p>0 1 2 3 4</p>
   * Where:
   *   <p>0 -> route id</p>
   *   <p>1,2,3,4 -> route stations ids</p>
   *
   * @param source String
   * @return Route object
   */
  @Override
  public Route parse(String source) {
    Route.RouteBuilder routeBuilder = Route.RouteBuilder.newInstance();

    Scanner scanner = new Scanner(source);

    int routeIndex = scanner.nextInt();

    logger.info("Parsing stations from route id={}", routeIndex);

    while (scanner.hasNext()) {
      int nextStation = scanner.nextInt();
      routeBuilder.withStation(nextStation);
    }


    return routeBuilder.withId(routeIndex).build();
  }

}
