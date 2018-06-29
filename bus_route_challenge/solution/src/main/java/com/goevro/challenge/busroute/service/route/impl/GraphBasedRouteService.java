package com.goevro.challenge.busroute.service.route.impl;

import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.service.route.RouteService;
import com.goevro.challenge.busroute.storage.RouteStorage;
import com.google.common.base.Preconditions;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Attempt to solve challenge using graph
 */
//@Service
public class GraphBasedRouteService implements RouteService {
  private static final Logger logger = LoggerFactory.getLogger(GraphBasedRouteService.class);
  private final RouteStorage storage;
  private Graph<Station> graph;

  public GraphBasedRouteService(RouteStorage routeStorage) {
    storage = routeStorage;
  }

  private void initGraph(Stream<Route> all) {
    MutableGraph<Station> stationsGraph = GraphBuilder.directed().build();

    all.forEach(route -> {
      logger.info(MessageFormat.format("Processing route id: {0}", route.getId()));

      route.getStations().stream().reduce((origin, next) -> {
        stationsGraph.putEdge(origin, next);
        return next;
      });

      logger.info(MessageFormat.format(
              "Processed {0} stations of route id: {1}",
              route.getStations().size(),
              route.getId()
      ));
    });

    graph = stationsGraph;
  }

  @Override
  public boolean isDirectlyConnected(Station fromNode, Station toNode) {
    Preconditions.checkNotNull(fromNode, "Missing source information");
    Preconditions.checkNotNull(toNode, "Missing destination information");

    initGraph(storage.findAll());

    return breadthFirstSearch(fromNode, toNode);
  }

  private boolean breadthFirstSearch(Station parentNode, Station destNode) {
    Queue<Station> bfsQueue = new LinkedList<>();
    Set<Integer> visitedNodes = new HashSet<>();

    bfsQueue.add(parentNode);
    visitedNodes.add(parentNode.getId());

    while (!bfsQueue.isEmpty()) {
      parentNode = bfsQueue.poll();

      for (Station neighborNode : graph.successors(parentNode)) {

        if (Objects.equals(destNode, neighborNode)) {
          return true;
        }

        if (!visitedNodes.contains(neighborNode.getId())) {
          visitedNodes.add(neighborNode.getId());
          bfsQueue.add(neighborNode);
        }
      }
    }

    return false;
  }

}
