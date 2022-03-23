package se.hernebring.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApplication {

  public static void main(String[] args) {
    if(args != null && args.length < 1) {
      System.err.println("Usage: java -jar target/search-0.0.1-SNAPSHOT.jar " +
          "\"doc1\" \"doc2\" ...");
    } else {
      SpringApplication.run(SearchApplication.class, args);
    }
  }

}
