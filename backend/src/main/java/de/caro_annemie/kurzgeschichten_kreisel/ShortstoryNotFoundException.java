package de.caro_annemie.kurzgeschichten_kreisel;

/**
 * exception to throw when shortstory is not found
 */
public class ShortstoryNotFoundException extends RuntimeException {

  public ShortstoryNotFoundException(Long id) {
    super("Could not find story with id: " + id);
  }
}
