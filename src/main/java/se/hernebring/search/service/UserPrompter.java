package se.hernebring.search.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service("prompter")
public class UserPrompter {

  //mock test requires non-final
  private Scanner scanner;
  private final PrintStream out;

  public UserPrompter() {
    scanner = new Scanner(System.in);
    this.out = System.out;
  }

  public String prompt(String message) {
    out.println(message);
    return scanner.nextLine();
  }
}
