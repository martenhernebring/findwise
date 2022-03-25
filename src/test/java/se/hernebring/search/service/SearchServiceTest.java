package se.hernebring.search.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.hernebring.search.repository.SearchPrompter;
import se.hernebring.search.repository.TfIdfSorter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
    assertFalse(searchServiceTest.searchForQuery());
  }

  @Test
  void searchForBrownSimulation() {
    List<String> list = new ArrayList<>();
    list.add("the brown fox jumped over the brown dog");
    list.add("the lazy brown dog sat in the corner");
    when(mockedSearchRepository.prompt(any(String.class))).thenReturn("brown");
    when(mockedDocumentRepository.search("brown")).thenReturn(list);
    searchServiceTest.searchForQuery();
  }

  @Test
  void searchForFoxSimulation() {
    List<String> list = new ArrayList<>();
    list.add("the red fox bit the lazy dog");
    list.add("the brown fox jumped over the brown dog");
    when(mockedSearchRepository.prompt(any(String.class))).thenReturn("fox");
    when(mockedDocumentRepository.search("fox")).thenReturn(list);
    searchServiceTest.searchForQuery();
  }
}
