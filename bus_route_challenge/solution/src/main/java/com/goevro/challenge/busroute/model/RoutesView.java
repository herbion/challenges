package com.goevro.challenge.busroute.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.util.Collection;

public class RoutesView {

  private Collection<Route> routes;

  private SetMultimap<Station, Route> connections = HashMultimap.create();

  public RoutesView(Collection<Route> routes) {
    this.routes = routes;

    for (Route route : routes) {
      for (Station station : route.getStations()) {
        connections.put(station, route);
      }
    }

  }

  public Collection<Route> getRoutesByStation(Station station) {
    return connections.get(station);
  }

  public Collection<Route> getAll() {
    return routes;
  }

}
