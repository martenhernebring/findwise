package se.hernebring.search;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SearchApplicationTests {

  @Test
  void nullArgumentIsIllegal() {
    var ex = assertThrows(IllegalArgumentException.class,
      () -> SearchApplication.main(null));
    assertEquals("Args must not be null", ex.getMessage());
  }

  @Test
  void emptyArgumentShouldPrintInstructions() {
    String[] empty = new String[0];
    final PrintStream standardErr = System.err;
    final ByteArrayOutputStream captor = new ByteArrayOutputStream();
    System.setErr(new PrintStream(captor));
    SearchApplication.main(empty);
    assertEquals("Usage: java -jar target/search-0.0.1-SNAPSHOT.jar " +
      "\"doc1\" \"doc2\" ...", captor.toString().trim());
    System.setErr(standardErr);
  }

}
