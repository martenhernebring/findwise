package se.hernebring.search.repository;

import org.springframework.stereotype.Repository;

import java.io.PrintStream;
import java.util.Scanner;

@Repository("search")
public class SearchPrompter implements QueryRepository {

  private Scanner scanner;
  private final PrintStream out;

  public SearchPrompter() {
    scanner = new Scanner(System.in);
    this.out = System.out;
  }


  @Override
  public String prompt(String message) {
    out.println(message);
    return scanner.nextLine();
  }
}
