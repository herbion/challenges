package com.goevro.challenge.busroute.model;

import java.util.*;

public class Route {
  private Integer id;
  private Collection<Station> stations;

  private Route(Integer id, Collection<Station> stations) {
    this.id = id;
    this.stations = stations;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Collection<Station> getStations() {
    return stations;
  }

  /**
   * Checks if station belongs to route
   *
   * @param station specific station
   * @return is station present on route
   */
  public boolean contains(Station station) {
    return stations.contains(station);
  }

  public static final class RouteBuilder {
    private Integer id;
    private Set<Station> stations = new LinkedHashSet<>();

    private RouteBuilder() {
    }

    public static RouteBuilder newInstance() {
      return new RouteBuilder();
    }

    public RouteBuilder withId(Integer id) {
      this.id = id;
      return this;
    }

    public RouteBuilder withStation(Integer stationId) {
      stations.add(Station.from(stationId));
      return this;
    }

    public Route build() {
      return new Route(id, stations);
    }
  }
}
