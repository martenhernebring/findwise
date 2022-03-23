package se.hernebring.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApplication {

  public static void main(String[] args) {
    if(args != null && args.length < 1) {
      throw new IllegalArgumentException("Args must not be empty");
    }
    SpringApplication.run(SearchApplication.class, args);
  }

}
