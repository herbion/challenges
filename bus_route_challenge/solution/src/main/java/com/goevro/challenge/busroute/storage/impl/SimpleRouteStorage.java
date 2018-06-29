package com.goevro.challenge.busroute.storage.impl;

import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.model.RoutesView;
import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.service.parser.FileResourceRouteParser;
import com.goevro.challenge.busroute.storage.RefreshableStorage;
import com.goevro.challenge.busroute.storage.RouteStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Simple in-memory route storage
 */
@Service
public class SimpleRouteStorage implements RouteStorage, RefreshableStorage {

  private static final Logger logger = LoggerFactory.getLogger(SimpleRouteStorage.class);
  private final FileResourceRouteParser fileResourceRouteParser;

  private RoutesView routesView;

  public SimpleRouteStorage(FileResourceRouteParser fileResourceRouteParser) {
    this.fileResourceRouteParser = fileResourceRouteParser;
    this.routesView = new RoutesView(Collections.emptyList());
  }

  @Override
  public Stream<Route> findAll() {
    return routesView.getAll().stream();
  }

  @Override
  public Collection<Route> findAvailableRoutes(Station station) {
    return routesView.getRoutesByStation(station);
  }

  @Override
  public void load(File resource) {
    logger.info("Loading data into repository from file: {}", resource.getName());
    Collection<Route> routes = fileResourceRouteParser.parse(resource);
    routesView = new RoutesView(routes);
    logger.info("Finished loading data into repository");
  }

}
