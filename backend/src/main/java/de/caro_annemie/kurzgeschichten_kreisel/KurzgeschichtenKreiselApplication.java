package de.caro_annemie.kurzgeschichten_kreisel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * initialiser class for spring boot
 */
@SpringBootApplication
public class KurzgeschichtenKreiselApplication {

	/**
	 * main method calling the spring boot application runner
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		SpringApplication.run(KurzgeschichtenKreiselApplication.class, args);
	}

}
