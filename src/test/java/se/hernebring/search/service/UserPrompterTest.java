package se.hernebring.search.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPrompterTest {

  @Mock
  private Scanner mockedScanner;

  @InjectMocks
  UserPrompter userPrompterTest;

  @Test
  void prompt() {
    when(mockedScanner.nextLine()).thenReturn("");
    String result = userPrompterTest.prompt("Test");
    assertEquals("", result);
  }
}