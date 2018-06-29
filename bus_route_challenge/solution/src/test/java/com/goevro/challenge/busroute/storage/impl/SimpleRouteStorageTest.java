package com.goevro.challenge.busroute.storage.impl;

import com.goevro.challenge.busroute.TestCaseHelper;
import com.goevro.challenge.busroute.service.parser.FileResourceRouteParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

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
    when(fakeParser.parse(any())).thenReturn(Arrays.asList(
            TestCaseHelper.newRoute(0, 1, 2, 3, 4),
            TestCaseHelper.newRoute(1, 2, 3, 4),
            TestCaseHelper.newRoute(4, 6, 3, 4)
    ));
    simpleRouteStorage.load(mock(File.class));
    assertEquals("should return all available routes", 3, simpleRouteStorage.findAll().count());
  }

  @Test
  public void load() {
    when(fakeParser.parse(any())).thenReturn(Arrays.asList(
            TestCaseHelper.newRoute(0, 1, 2, 3, 4),
            TestCaseHelper.newRoute(1, 2, 3, 4),
            TestCaseHelper.newRoute(4, 6, 3, 4)
    ));
    simpleRouteStorage.load(mock(File.class));

    when(fakeParser.parse(any())).thenReturn(Arrays.asList(
            TestCaseHelper.newRoute(0, 1, 2, 3, 4),
            TestCaseHelper.newRoute(1, 2, 3, 4)
    ));

    simpleRouteStorage.load(mock(File.class));

    assertEquals("should return all available routes after reload", 2, simpleRouteStorage.findAll().count());
  }

}