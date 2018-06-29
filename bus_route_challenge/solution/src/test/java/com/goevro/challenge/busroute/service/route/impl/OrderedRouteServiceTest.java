package com.goevro.challenge.busroute.service.route.impl;

import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.storage.RouteStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static com.goevro.challenge.busroute.TestCaseHelper.newRoute;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderedRouteServiceTest {

  private OrderedRouteService positionBasedRouteService;
  private RouteStorage fakeStorage;

  @Before
  public void setup() {
    fakeStorage = mock(RouteStorage.class);
    positionBasedRouteService = new OrderedRouteService(fakeStorage);
  }

  @Test
  public void isDirectlyConnected() {
    when(fakeStorage.findAll()).thenAnswer(invocationOnMock -> Stream.of(
            newRoute(0, 0, 1, 2, 3, 4),
            newRoute(1, 1, 3, 1, 6, 5),
            newRoute(2, 1, 0, 6, 4)
    ));

    assertTrue("nodes on same routes should have direct connection", isConnected(3, 6));
    assertFalse("nodes between routes doesn't have direct connection", isConnected(2, 6));
    assertFalse("nodes on same route should have ordered connection", isConnected(6, 3));
    assertTrue("node should have reflexive connection", isConnected(3, 3));

    assertFalse("missing nodes should not have connection", isConnected(999, 6));
    assertFalse("missing nodes should not have connection", isConnected(6, 999));
    assertFalse("missing nodes should not have connection", isConnected(999, 999));
  }

  private boolean isConnected(int from, int to) {
    return positionBasedRouteService.isDirectlyConnected(Station.from(from), Station.from(to));
  }
}