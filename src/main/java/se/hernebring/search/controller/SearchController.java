package se.hernebring.search.controller;

import org.springframework.stereotype.Controller;
import se.hernebring.search.exception.IllegalQueryException;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.service.TfIdfCalculator;
import se.hernebring.search.service.TfIdfEngine;
import se.hernebring.search.service.UserPrompter;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

  private final TfIdfCalculator sorter;
  private final UserPrompter prompter;
  private final TfIdfEngine engine;

  public SearchController(TfIdfCalculator sorter, UserPrompter prompter,
                          TfIdfEngine engine) {
    this.sorter = sorter;
    this.prompter = prompter;
    this.engine = engine;
  }

  public void index(String[] args) {
    if (args[0].equals("default")) {
      sorter.index(new String[]{
        "the brown fox jumped over the brown dog",
        "the lazy brown dog sat in the corner",
        "the red fox bit the lazy dog"});
    }
    else
      sorter.index(args);
  }

  public List<String> searchOrShutdown() {
    String query = prompter
      .prompt("Type word and hit enter to search (empty to quit)");
    if(query.isEmpty())
      throw new ShutdownRequestedException("Empty search: Quit requested");
    try {
      return engine.search(query);
    } catch (IllegalQueryException ex) {
      return new ArrayList<>(List.of(ex.getMessage()));
    }

  }
}
