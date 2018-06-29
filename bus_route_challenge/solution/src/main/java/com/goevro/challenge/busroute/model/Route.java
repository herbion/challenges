package com.goevro.challenge.busroute.model;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Route route = (Route) o;
    return Objects.equals(id, route.id) &&
            Objects.equals(stations, route.stations);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, stations);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
                      .add("id", id)
                      .add("stations", stations)
                      .toString();
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
