package de.caro_annemie.kurzgeschichten_kreisel;

public class KurzgeschichteNotFoundException extends RuntimeException {

    public KurzgeschichteNotFoundException(Long id) {
      super("Could not find story with id: " + id);
    }
  }
