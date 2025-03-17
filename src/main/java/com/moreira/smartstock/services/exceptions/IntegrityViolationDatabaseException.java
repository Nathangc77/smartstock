package com.moreira.smartstock.services.exceptions;

public class IntegrityViolationDatabaseException extends RuntimeException {
  public IntegrityViolationDatabaseException(String message) {
    super(message);
  }
}
