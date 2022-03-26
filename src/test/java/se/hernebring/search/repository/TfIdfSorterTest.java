package se.hernebring.search.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
    expectedList.put(0.11005703690980077, "the brown fox jumped over the brown dog");
    expectedList.put(0.0990513332188207, "the lazy brown dog sat in the corner");
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
    expectedList.put(0.0990513332188207, "the brown fox jumped over the brown dog");
    expectedList.put(0.10062357660324642, "the red fox bit the lazy dog");
//    TreeMap<Double, String> actualList = repository.search("brown");
//    TreeMap<Double, String> expectedList = new TreeMap<>();
//    expectedList.put(0.11005703690980077, "the brown fox jumped over the brown dog");
//    expectedList.put(0.0990513332188207, "the lazy brown dog sat in the corner");
    assertEquals(expectedList, actualList);
  }
}
