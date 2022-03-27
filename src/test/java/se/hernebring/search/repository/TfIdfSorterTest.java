package se.hernebring.search.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

  @Test
  void sameTfIdfValueGetsSubtractedByMinValue() {
    String[] documents = {"this is a a sample",
      "this is another another example example example"};
    repository.index(documents);
    TreeMap<Double, String> result = repository.search("this");
    TreeMap<Double, String> expected = new TreeMap<>(Collections.reverseOrder());
    expected.put(-1.0E-18, "this is another another example example example");
    expected.put(0.0, "this is a a sample");
    assertEquals(expected, result);
  }

  @Test
  void wikipediaExampleShouldReturnZeroDot129() {
    String[] documents = {"this is a a sample",
      "this is another another example example example"};
    repository.index(documents);
    TreeMap<Double, String> result = repository.search("example");
    TreeMap<Double, String> expected = new TreeMap<>();
    expected.put(0.129, "this is another another example example example");
    assertEquals(expected.firstKey().toString(),
      String.format(Locale.US, "%.3f", result.firstKey()));
  }
}
