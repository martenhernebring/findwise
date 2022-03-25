package se.hernebring.search.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TfIdfSorterTest {

  private DocumentRepository repository = new TfIdfSorter();

  @Test
  void searchForBrownShouldReturnDocument1And2() {
    String[] documents = {"the brown fox jumped over the brown dog",
      "the lazy brown dog sat in the corner",
      "the red fox bit the lazy dog"};
    repository.index(documents);
    List<String> actualList = repository.search("brown");
    List<String> expectedList = new ArrayList<>();
    expectedList.add("the brown fox jumped over the brown dog");
    expectedList.add("the lazy brown dog sat in the corner");
    assertEquals(expectedList, actualList);
  }

  @Test
  void searchForFoxShouldReturnDocument3And1() {
    String[] documents = {"the brown fox jumped over the brown dog",
      "the lazy brown dog sat in the corner",
      "the red fox bit the lazy dog"};
    repository.index(documents);
    List<String> actualList = repository.search("brown");
    List<String> expectedList = new ArrayList<>();
    expectedList.add("the red fox bit the lazy dog");
    expectedList.add("the brown fox jumped over the brown dog");
    assertEquals(expectedList, actualList);
  }
}
