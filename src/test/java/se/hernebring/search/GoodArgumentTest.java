package se.hernebring.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.hernebring.search.exception.ShutdownRequestedException;
import se.hernebring.search.service.SearchService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoodArgumentTest {

  @Mock
  private SearchService mockedSearchService;

  @InjectMocks
  private SearchApplication searchApplicationTest;

  @Test
  void runSimulationWithGoodArgsAndUserRequestingShutdown() {
    final PrintStream standardOut = System.out;
    final ByteArrayOutputStream captor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(captor));
    String[] multipleNonBlank = new String[2];
    multipleNonBlank[0] = "the brown fox jumped over the brown dog";
    multipleNonBlank[1] = "the lazy brown dog sat in the corner";
    doNothing().when(mockedSearchService).index(any());
    when(mockedSearchService.searchUntilShutdown()).thenThrow(new ShutdownRequestedException("Test"));
    searchApplicationTest.run(multipleNonBlank);
    assertEquals("Thanks for using our Searching App. See you again!", captor.toString().trim());
    System.setOut(standardOut);
  }
}