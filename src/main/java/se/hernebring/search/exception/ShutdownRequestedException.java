package se.hernebring.search.exception;

public class ShutdownRequestedException extends RuntimeException {
  public ShutdownRequestedException(String message) {
    super(message);
  }
}
