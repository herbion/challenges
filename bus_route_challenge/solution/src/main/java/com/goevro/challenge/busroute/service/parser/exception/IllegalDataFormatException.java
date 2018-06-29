package com.goevro.challenge.busroute.service.parser.exception;

import java.io.File;

public class IllegalDataFormatException extends RuntimeException {
  private File source;

  public IllegalDataFormatException(File source, Exception e) {
    super(e);
    this.source = source;
  }

  public File getSource() {
    return source;
  }

}
