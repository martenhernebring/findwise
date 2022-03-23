package se.hernebring.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApplication {

  private static final String INSTRUCTIONS = "Usage: java -jar " +
    "target/search-0.0.1-SNAPSHOT.jar \"doc1\" \"doc2\" ...";

  public static void main(String[] args) {
    if(args != null) {
      verify(args);
    }
    SpringApplication.run(SearchApplication.class, args);
  }

  private static void verify(String[] args) {
    if(args.length < 1)
      System.err.println(INSTRUCTIONS);
    else {
      mustContainTwoTexts(args);
    }
  }

  private static void mustContainTwoTexts(String[] args) {
    boolean first = true;
    boolean second = false;
    for(String arg: args) {
      if(first) {
        first = arg.isBlank();
      } else if (!second) {
        second = !arg.isBlank();
      } else
        break;
    }
    if(!second) {
        System.err.println(INSTRUCTIONS +
          " (must contain several non-white documents)");
    }
  }

}
