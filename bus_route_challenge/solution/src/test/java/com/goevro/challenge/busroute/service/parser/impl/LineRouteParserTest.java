package com.goevro.challenge.busroute.service.parser.impl;


import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.model.Station;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class LineRouteParserTest {

  private LineRouteParser lineRouteParser = new LineRouteParser();

  @Test
  public void testParse() {
    Route route = lineRouteParser.parse("0 1 2 3 4");

    assertEquals("should parse route id", Integer.valueOf(0), route.getId());
    assertEquals("should parse all stations", 4, route.getStations().size());

    Iterator<Station> actualStations = route.getStations().iterator();
    for (Integer expectedStationId : Arrays.asList(1, 2, 3, 4)) {
      assertEquals("should preserve station order", expectedStationId, actualStations.next().getId());
    }
  }

  @Test
  public void testIgnoreWhiteSpace() {
    Route route = lineRouteParser.parse(" 0 1     2 3    4          5  ");

    assertEquals("should parse route id", Integer.valueOf(0), route.getId());
    assertEquals("should parse all stations", 5, route.getStations().size());
  }

  @Test(expected = RuntimeException.class)
  public void testBrokenFormat() {
    lineRouteParser.parse(" 0 I'M AN ERROR");
  }

}