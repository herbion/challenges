package com.goevro.challenge.busroute.service.parser;

/**
 * General use interface for data transformer
 *
 * @param <T> input data
 * @param <R> output data
 */
public interface Parser<T, R> {

  R parse(T source);

}
