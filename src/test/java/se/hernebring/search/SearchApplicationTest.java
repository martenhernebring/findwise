package se.hernebring.search;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.hernebring.search.service.SearchService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@Disabled
class SearchApplicationTest {

  @Mock
  private SearchService mockedSearchService;

  @InjectMocks
  private SearchApplication searchApplicationTest;

  @Test
  void run() throws Exception {
    String[] multipleNonBlank = new String[2];
    multipleNonBlank[0] = "the brown fox jumped over the brown dog";
    multipleNonBlank[1] = "the lazy brown dog sat in the corner";
    doNothing().when(mockedSearchService).index(any());
    doReturn(false).when(mockedSearchService).searchForQuery();
    searchApplicationTest.run(multipleNonBlank);
  }
}