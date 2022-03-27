package se.hernebring.search.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.repository.SearchPrompter;
import se.hernebring.search.repository.TfIdfSorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

  @Mock
  private TfIdfSorter mockedDocumentRepository;

  @Mock
  private SearchPrompter mockedSearchRepository;

  @InjectMocks
  private SearchService searchServiceTest;

  @Test
  void indexArgsShouldNotThrowSimulation() {
    String[] documents = {"the brown fox jumped over the brown dog",
      "the lazy brown dog sat in the corner",
      "the red fox bit the lazy dog"};
    doNothing().when(mockedDocumentRepository).index(any());
    searchServiceTest.index(documents);
  }

  @Test
  void userRequestShutdownSimulation() {
    when(mockedSearchRepository.prompt(any(String.class))).thenReturn("");
    assertThrows(ShutdownRequestedException.class,
      () -> searchServiceTest.searchOrShutdown());
  }

  @Test
  void searchForBrownSimulation() {
    List<String> expected = new ArrayList<>();
    expected.add("the brown fox jumped over the brown dog");
    expected.add("the lazy brown dog sat in the corner");
    when(mockedSearchRepository.prompt(any(String.class))).thenReturn("brown");
    TreeMap<Double, String> reverseMap = new TreeMap<>(Collections.reverseOrder());
    reverseMap.put(0.04402281476392031, expected.get(0));
    reverseMap.put(0.022011407381960155, expected.get(1));
    when(mockedDocumentRepository.search("brown")).thenReturn(reverseMap);
    var result = searchServiceTest.searchOrShutdown();
    assertEquals(expected, result);
  }

  @Test
  void searchForFoxSimulation() {
    List<String> expected = new ArrayList<>();
    expected.add("the red fox bit the lazy dog");
    expected.add("the brown fox jumped over the brown dog");
    when(mockedSearchRepository.prompt(any(String.class))).thenReturn("fox");
    TreeMap<Double, String> reverseMap = new TreeMap<>(Collections.reverseOrder());
    reverseMap.put(0.025155894150811604, expected.get(0));
    reverseMap.put(0.022011407381960155, expected.get(1));
    when(mockedDocumentRepository.search("fox")).thenReturn(reverseMap);
    var result = searchServiceTest.searchOrShutdown();
    assertEquals(expected, result);
  }
}
