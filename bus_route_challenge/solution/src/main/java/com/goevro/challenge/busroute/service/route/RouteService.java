package com.goevro.challenge.busroute.service.route;

import com.goevro.challenge.busroute.model.Station;

public interface RouteService {

  boolean isDirectlyConnected(Station source, Station destination);

}
