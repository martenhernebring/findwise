package se.hernebring.search.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import se.hernebring.search.SearchApplication;
import se.hernebring.search.service.SearchService;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchPrompterTest {

  @Mock
  private Scanner mockedScanner;

  @InjectMocks
  SearchPrompter searchPrompterTest;

  @Test
  void prompt() {
    when(mockedScanner.nextLine()).thenReturn("");
    String result = searchPrompterTest.prompt("Test");
    assertEquals("", result);
  }
}