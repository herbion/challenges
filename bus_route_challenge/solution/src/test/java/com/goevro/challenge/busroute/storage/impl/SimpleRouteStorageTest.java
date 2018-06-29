package com.goevro.challenge.busroute.storage.impl;

import com.goevro.challenge.busroute.TestCaseHelper;
import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.model.Station;
import com.goevro.challenge.busroute.service.parser.FileResourceRouteParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SimpleRouteStorageTest {

  private SimpleRouteStorage simpleRouteStorage;
  private FileResourceRouteParser fakeParser;

  @Before
  public void setup() {
    fakeParser = mock(FileResourceRouteParser.class);
    simpleRouteStorage = new SimpleRouteStorage(fakeParser);
  }

  @Test
  public void findAll() {
    when(fakeParser.parse(any())).thenReturn(asList(
            TestCaseHelper.newRoute(0, 1, 2, 3, 4),
            TestCaseHelper.newRoute(1, 2, 3, 4),
            TestCaseHelper.newRoute(4, 6, 3, 4)
    ));
    simpleRouteStorage.load(mock(File.class));
    assertEquals("should return all available routes", 3, simpleRouteStorage.findAll().count());
  }

  @Test
  public void findRoutesByStation() {
    Route route0 = TestCaseHelper.newRoute(0, 1, 2, 3, 4);
    Route route1 = TestCaseHelper.newRoute(1, 2, 3, 4);
    Route route4 = TestCaseHelper.newRoute(4, 6, 3, 4);

    when(fakeParser.parse(any())).thenReturn(asList(route0, route1, route4));

    simpleRouteStorage.load(mock(File.class));

    Assert.assertTrue(
            "should return all available routes by station",
            isSameRoutes(simpleRouteStorage.findAvailableRoutes(Station.from(1)), singletonList(route0))
    );
    Assert.assertTrue(
            "should return all available routes by station",
            isSameRoutes(simpleRouteStorage.findAvailableRoutes(Station.from(2)), asList(route0, route1))
    );
    Assert.assertTrue(
            "should return all available routes",
            isSameRoutes(simpleRouteStorage.findAvailableRoutes(Station.from(3)), asList(route0, route1, route4))
    );

    Assert.assertTrue(
            "should return all available routes",
            isSameRoutes(simpleRouteStorage.findAvailableRoutes(Station.from(4)), asList(route0, route1, route4))
    );

    Assert.assertTrue(
            "should return all available routes",
            isSameRoutes(simpleRouteStorage.findAvailableRoutes(Station.from(6)), singletonList(route4))
    );

    Assert.assertTrue(
            "should correctly handle non existing stations",
            isSameRoutes(simpleRouteStorage.findAvailableRoutes(Station.from(999)), emptyList())
    );
  }

  @Test
  public void load() {
    when(fakeParser.parse(any())).thenReturn(asList(
            TestCaseHelper.newRoute(0, 1, 2, 3, 4),
            TestCaseHelper.newRoute(1, 2, 3, 4),
            TestCaseHelper.newRoute(4, 6, 3, 4)
    ));
    simpleRouteStorage.load(mock(File.class));

    when(fakeParser.parse(any())).thenReturn(asList(
            TestCaseHelper.newRoute(0, 1, 2, 3, 4),
            TestCaseHelper.newRoute(1, 2, 3, 4)
    ));

    simpleRouteStorage.load(mock(File.class));

    assertEquals("should return all available routes after reload", 2, simpleRouteStorage.findAll().count());
  }

  private boolean isSameRoutes(Collection<Route> actualRoutes, Collection<Route> expectedRoutes) {
    return actualRoutes.containsAll(expectedRoutes);
  }

}