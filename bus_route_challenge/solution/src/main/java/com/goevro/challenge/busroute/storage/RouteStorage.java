package com.goevro.challenge.busroute.storage;

import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.model.Station;

import java.util.Collection;

public interface RouteStorage extends Storage<Route>, RefreshableStorage {

  Collection<Route> findAvailableRoutes(Station station);
}
