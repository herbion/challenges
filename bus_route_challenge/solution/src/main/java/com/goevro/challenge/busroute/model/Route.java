package com.goevro.challenge.busroute.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Route {
  private Integer id;
  private Map<Station, Integer> stationOrder;

  private Route(Integer id, Map<Station, Integer> stationOrder) {
    this.id = id;
    this.stationOrder = stationOrder;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Collection<Station> getStations() {
    return stationOrder.keySet();
  }

  /**
   * Returns station position on current route
   *
   * @param station specific station
   * @return index of station
   */
  public Integer indexOf(Station station) {
    return stationOrder.get(station);
  }

  /**
   * Checks if station belongs to route
   *
   * @param station specific station
   * @return is station present on route
   */
  public boolean contains(Station station) {
    return stationOrder.containsKey(station);
  }

  public static final class RouteBuilder {
    private Integer id;
    private Map<Station, Integer> stationOrder = new LinkedHashMap<>();

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
      stationOrder.put(Station.from(stationId), stationOrder.size());
      return this;
    }

    public Route build() {
      return new Route(id, stationOrder);
    }
  }
}
