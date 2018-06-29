package com.goevro.challenge.busroute.web.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goevro.challenge.busroute.model.Station;

public class DirectConnectionResponse {
  @JsonProperty("dep_sid")
  private Integer departmentStation;
  @JsonProperty("arr_sid")
  private Integer arrivalStation;
  @JsonProperty("direct_bus_route")
  private Boolean isConnected;

  public DirectConnectionResponse(Station department, Station arrival, boolean isConnected) {
    this.departmentStation = department.getId();
    this.arrivalStation = arrival.getId();
    this.isConnected = isConnected;
  }

  public Integer getDepartmentStation() {
    return departmentStation;
  }

  public void setDepartmentStation(Integer departmentStation) {
    this.departmentStation = departmentStation;
  }

  public Integer getArrivalStation() {
    return arrivalStation;
  }

  public void setArrivalStation(Integer arrivalStation) {
    this.arrivalStation = arrivalStation;
  }

  public Boolean getIsConnected() {
    return isConnected;
  }

  public void setIsConnected(Boolean isConnected) {
    this.isConnected = isConnected;
  }
}
