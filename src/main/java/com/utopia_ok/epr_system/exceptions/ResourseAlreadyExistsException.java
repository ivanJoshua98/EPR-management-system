package com.utopia_ok.epr_system.exceptions;

public class ResourseAlreadyExistsException extends RuntimeException {
  public ResourseAlreadyExistsException(String message) {
    super(message);
  }
}
