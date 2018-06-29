package com.goevro.challenge.busroute.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Station {
  private Integer id;

  public Station(Integer id) {
    this.id = id;
  }

  public static Station from(Integer id) {
    return new Station(id);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Station station = (Station) o;
    return Objects.equals(id, station.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
                      .add("id", id)
                      .toString();
  }
}
