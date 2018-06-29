package com.goevro.challenge.busroute;

import com.goevro.challenge.busroute.model.Route;

import java.io.File;

public class TestCaseHelper {

  public static Route newRoute(Integer id, Integer... stations) {
    Route.RouteBuilder builder = Route.RouteBuilder.newInstance().withId(id);

    for (Integer station : stations) {
      builder.withStation(station);
    }

    return builder.build();
  }

  public static File getResource(String resourceName) {
    ClassLoader classLoader = TestCaseHelper.class.getClassLoader();
    return new File(classLoader.getResource(resourceName).getFile());
  }
}
