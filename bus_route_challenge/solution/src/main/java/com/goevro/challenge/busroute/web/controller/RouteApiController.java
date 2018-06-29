package com.goevro.challenge.busroute.web.controller;

import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.service.route.RouteService;
import com.goevro.challenge.busroute.web.wrapper.DirectConnectionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RouteApiController {

  private RouteService routeService;

  public RouteApiController(RouteService routeService) {
    this.routeService = routeService;
  }

  @GetMapping("/direct")
  public DirectConnectionResponse findDirectConnection(@RequestParam("dep_sid") Integer departmentStation,
          @RequestParam("arr_sid") Integer arrivalStation) {

    Station department = Station.from(departmentStation);
    Station arrival = Station.from(arrivalStation);
    boolean isConnected = routeService.isDirectlyConnected(department, arrival);

    return new DirectConnectionResponse(department, arrival, isConnected);
  }
}
