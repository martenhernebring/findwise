package se.hernebring.search.repository;

import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TfIdfSorterTest {

  private final DocumentRepository repository = new TfIdfSorter();

  @Test
  void searchForBrownShouldReturnDocument1And2() {
    String[] documents = {"the brown fox jumped over the brown dog",
      "the lazy brown dog sat in the corner",
      "the red fox bit the lazy dog"};
    repository.index(documents);
    TreeMap<Double, String> actualList = repository.search("brown");
    TreeMap<Double, String> expectedList = new TreeMap<>();
    expectedList.put(0.04402281476392031, "the brown fox jumped over the brown dog");
    expectedList.put(0.022011407381960155, "the lazy brown dog sat in the corner");
    assertEquals(expectedList, actualList);
  }

  @Test
  void searchForFoxShouldReturnDocument3And1() {
    String[] documents = {"the brown fox jumped over the brown dog",
      "the lazy brown dog sat in the corner",
      "the red fox bit the lazy dog"};
    repository.index(documents);
    TreeMap<Double, String> actualList = repository.search("fox");
    TreeMap<Double, String> expectedList = new TreeMap<>();
    expectedList.put(0.022011407381960155, "the brown fox jumped over the brown dog");
    expectedList.put(0.025155894150811604, "the red fox bit the lazy dog");
    assertEquals(expectedList, actualList);
  }
}
