package se.hernebring.search;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.service.SearchService;

@SpringBootApplication
public class SearchApplication implements CommandLineRunner {

  final SearchService service;

  public SearchApplication(SearchService service) {
    this.service = service;
  }

  public static void main(String[] args) {
    SpringApplication.run(SearchApplication.class, args);
  }

  @Override
  public void run(String... args) {
    try {
      Argument.verify(args);
      service.index(args);
      while (true)
        service.searchUntilShutdown().forEach(System.out::println);
    } catch (IllegalArgumentException ex) {
      System.err.println(ex.getMessage());
    } catch (ShutdownRequestedException ex) {
      System.out.println("Thanks for using our Searching App. See you again!");
    }
  }
}
