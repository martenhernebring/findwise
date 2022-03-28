package se.hernebring.search.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.service.TfIdfCalculator;
import se.hernebring.search.service.TfIdfEngine;
import se.hernebring.search.service.UserPrompter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchControllerTest {

  @Mock
  private TfIdfCalculator mockedSorter;

  @Mock
  private UserPrompter mockedPrompter;

  @Mock
  private TfIdfEngine mockedEngine;

  @InjectMocks
  private SearchController searchControllerTest;

  @Test
  void userRequestShutdownSimulation() {
    when(mockedPrompter.prompt(any(String.class))).thenReturn("");
    assertThrows(ShutdownRequestedException.class,
      () -> searchControllerTest.searchOrShutdown());
  }

  @Test
  void simulateIndexingTwoTexts() {
    doNothing().when(mockedSorter).index(any());
    searchControllerTest.index(new String[]{"a", "b"});
    verify(mockedSorter, times(1)).index(any());
  }

  @Test
  void simulateIndexingDefault() {
    doNothing().when(mockedSorter).index(any());
    searchControllerTest.index(new String[]{"default"});
    verify(mockedSorter, times(1)).index(any());
  }

  @Test
  void searchForBrownSimulation() {
    List<String> expected = new ArrayList<>();
    expected.add("the brown fox jumped over the brown dog");
    expected.add("the lazy brown dog sat in the corner");
    when(mockedPrompter.prompt(any(String.class))).thenReturn("brown");
    TreeMap<Double, String> revMap = new TreeMap<>(Collections.reverseOrder());
    revMap.put(0.04402281476392031, expected.get(0));
    revMap.put(0.022011407381960155, expected.get(1));
    when(mockedEngine.search("brown")).thenReturn(revMap);
    assertEquals(expected, searchControllerTest.searchOrShutdown());
  }

  @Test
  void searchForFoxSimulation() {
    List<String> expected = new ArrayList<>();
    expected.add("the red fox bit the lazy dog");
    expected.add("the brown fox jumped over the brown dog");
    when(mockedPrompter.prompt(any(String.class))).thenReturn("fox");
    TreeMap<Double, String> revMap = new TreeMap<>(Collections.reverseOrder());
    revMap.put(0.025155894150811604, expected.get(0));
    revMap.put(0.022011407381960155, expected.get(1));
    when(mockedEngine.search("fox")).thenReturn(revMap);
    assertEquals(expected, searchControllerTest.searchOrShutdown());
  }
}
