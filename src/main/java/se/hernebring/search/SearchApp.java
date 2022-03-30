package se.hernebring.search;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.hernebring.search.controller.SearchController;
import se.hernebring.search.exception.ShutdownRequestedException;

@SpringBootApplication
public class SearchApp implements CommandLineRunner {

  private final SearchController controller;

  public SearchApp(SearchController controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    SpringApplication.run(SearchApp.class, args);
  }

  @Override
  public void run(String... args) {
    try {
      Argument.verify(args);
      controller.index(args);
      while (true) {
        controller.searchOrShutdown().forEach(System.out::println);
      }
    } catch (IllegalArgumentException ex) {
      System.err.println(ex.getMessage());
    } catch (ShutdownRequestedException ex) {
      System.out.println("Thanks for using our Searching App. See you again!");
    }
  }
}
