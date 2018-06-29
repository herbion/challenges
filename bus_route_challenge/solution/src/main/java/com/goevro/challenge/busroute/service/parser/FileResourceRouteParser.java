package com.goevro.challenge.busroute.service.parser;


import com.goevro.challenge.busroute.model.Route;

import java.io.File;
import java.util.Collection;

/**
 * Transform data from {@link File} representation to {@link Collection<Route>}
 */
public interface FileResourceRouteParser extends Parser<File, Collection<Route>> {
}
