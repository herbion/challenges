package com.goevro.challenge.busroute.service.parser.impl;

import com.goevro.challenge.busroute.TestCaseHelper;
import com.goevro.challenge.busroute.model.Route;
import com.goevro.challenge.busroute.service.parser.exception.IllegalDataFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Collection;

import static com.goevro.challenge.busroute.TestCaseHelper.newRoute;
import static org.mockito.ArgumentMatchers.any;

public class FileRouteParserTest {
  private static final String ROUTE_DATA = "route-data-3";
  private static final String ROUTE_DATA_BROKEN = "route-data-broken";

  private FileRouteParser fileRouteParser;

  @Before
  public void setup() {
    LineRouteParser fakeLineParser = Mockito.mock(LineRouteParser.class);
    Mockito.when(fakeLineParser.parse(any())).thenReturn(newRoute(0, 1, 2, 3));
    fileRouteParser = new FileRouteParser(fakeLineParser);
  }

  @Test
  public void testParse() {
    Collection<Route> parsedRoutes = fileRouteParser.parse(TestCaseHelper.getResource(ROUTE_DATA));
    Assert.assertEquals("should parse all routes", 3, parsedRoutes.size());
  }

  @Test(expected = IllegalDataFormatException.class)
  public void testBrokenInputData() {
    fileRouteParser.parse(TestCaseHelper.getResource(ROUTE_DATA_BROKEN));
  }

  @Test(expected = RuntimeException.class)
  public void testBrokenInput() {
    fileRouteParser.parse(new File("doesn't exist"));
  }

}