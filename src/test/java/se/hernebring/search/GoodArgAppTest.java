package se.hernebring.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.hernebring.search.controller.SearchController;
import se.hernebring.search.exception.ShutdownRequestedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoodArgAppTest {

  @Mock
  private SearchController mockedSearchController;

  @InjectMocks
  private SearchApp searchAppTest;

  @Test
  void runSimulationWithGoodArgsAndUserRequestingShutdown() {
    final PrintStream standardOut = System.out;
    final ByteArrayOutputStream captor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(captor));
    String[] defaultArgs = {"default"};
    doNothing().when(mockedSearchController).index(any());
    when(mockedSearchController.searchOrShutdown())
      .thenThrow(new ShutdownRequestedException("Test"));
    searchAppTest.run(defaultArgs);
    assertEquals("Thanks for using our Searching App. See you again!",
      captor.toString().trim());
    System.setOut(standardOut);
  }
}