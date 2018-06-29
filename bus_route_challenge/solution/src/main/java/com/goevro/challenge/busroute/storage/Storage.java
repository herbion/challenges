package com.goevro.challenge.busroute.storage;

import java.util.stream.Stream;

public interface Storage<T> {

  Stream<T> findAll();

}
