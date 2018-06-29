package com.goevro.challenge.busroute.service.route.impl;

import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.service.route.RouteService;
import com.goevro.challenge.busroute.storage.RouteStorage;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class RouteServiceImpl implements RouteService {

  private RouteStorage routeRepository;

  public RouteServiceImpl(RouteStorage routeRepository) {
    this.routeRepository = routeRepository;
  }

  /**
   * Check if direct connection exists from station source to station destination,
   * order of stations inside route does NOT matter. For example:
   * Given route: [1,2,3,4]
   * - 1 -> 3 = true (has direct connection)
   * - 3 -> 1 = true (has direct connection)
   * - 1 -> 1 = true
   *
   * @param fromNode department station
   * @param toNode   arrival station
   * @return are stations connected directly
   */
  @Override
  public boolean isDirectlyConnected(Station fromNode, Station toNode) {
    Preconditions.checkNotNull(fromNode, "Missing source information");
    Preconditions.checkNotNull(toNode, "Missing destination information");

    return routeRepository.findAll().anyMatch(route -> route.contains(fromNode) && route.contains(toNode));
  }

}
