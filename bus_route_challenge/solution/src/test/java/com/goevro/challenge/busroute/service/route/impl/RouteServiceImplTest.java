package com.goevro.challenge.busroute.service.route.impl;

import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.storage.RouteStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static com.goevro.challenge.busroute.TestCaseHelper.newRoute;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouteServiceImplTest {
  private RouteServiceImpl routeService;
  private RouteStorage fakeStorage;

  @Before
  public void setup() {
    fakeStorage = mock(RouteStorage.class);
    routeService = new RouteServiceImpl(fakeStorage);
  }

  @Test
  public void isDirectlyConnected() {
    Route route0 = newRoute(0, 0, 1, 2, 3, 4);
    Route route1 = newRoute(1, 1, 3, 1, 6, 5);
    Route route4 = newRoute(2, 1, 0, 6, 4);

    when(fakeStorage.findAll()).thenAnswer(invocationOnMock -> Stream.of(route0, route1, route4));

    when(fakeStorage.findAvailableRoutes(Station.from(1))).thenReturn(asList(route0, route1, route4));
    when(fakeStorage.findAvailableRoutes(Station.from(2))).thenReturn(singletonList(route0));
    when(fakeStorage.findAvailableRoutes(Station.from(3))).thenReturn(asList(route0, route1));
    when(fakeStorage.findAvailableRoutes(Station.from(4))).thenReturn(singletonList(route4));
    when(fakeStorage.findAvailableRoutes(Station.from(5))).thenReturn(singletonList(route1));
    when(fakeStorage.findAvailableRoutes(Station.from(6))).thenReturn(asList(route1, route4));

    assertTrue("nodes on same routes should have direct connection", isConnected(3, 6));
    assertFalse("nodes between routes doesn't have direct connection", isConnected(2, 6));
    assertTrue("nodes on same route should have connection in both ways", isConnected(6, 3));
    assertTrue("node should have reflexive connection", isConnected(3, 3));

    assertFalse("missing nodes should not have connection (department missing)", isConnected(999, 6));
    assertFalse("missing nodes should not have connection (arrival missing)", isConnected(6, 999));
    assertFalse("missing nodes should not have connection (both missing)", isConnected(999, 999));
  }

  private boolean isConnected(int from, int to) {
    return routeService.isDirectlyConnected(Station.from(from), Station.from(to));
  }

}