package se.hernebring.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchApplicationTests {

  private final PrintStream standardErr = System.err;
  private final ByteArrayOutputStream captor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setErr(new PrintStream(captor));
  }

  @AfterEach
  public void tearDown() {
    System.setErr(standardErr);
  }

  @Test
  void nullArgumentIsIllegal() {
    var ex = assertThrows(IllegalArgumentException.class,
      () -> SearchApplication.main(null));
    assertEquals("Args must not be null", ex.getMessage());
  }

  @Test
  void emptyArgumentShouldPrintInstructions() {
    String[] empty = new String[0];
    SearchApplication.main(empty);
    assertEquals("Usage: java -jar target/search-0.0.1-SNAPSHOT.jar " +
      "\"doc1\" \"doc2\" ...", captor.toString().trim());
  }

  @Test
  void oneDocumentOnlyShouldPrintInstructions() {
    String[] justOneDoc = new String[1];
    justOneDoc[0] = "the brown fox jumped over the brown dog";
    SearchApplication.main(justOneDoc);
    assertEquals("Usage: java -jar target/search-0.0.1-SNAPSHOT.jar " +
        "\"doc1\" \"doc2\" ... (must contain several non-white documents)",
      captor.toString().trim());
  }

  @Test
  void oneNonBlankDocumentOnlyShouldPrintInstructions() {
    String[] justOneNonWhite = new String[2];
    justOneNonWhite[0] = "the brown fox jumped over the brown dog";
    justOneNonWhite[1] = " ";
    SearchApplication.main(justOneNonWhite);
    assertEquals("Usage: java -jar target/search-0.0.1-SNAPSHOT.jar " +
        "\"doc1\" \"doc2\" ... (must contain several non-white documents)",
      captor.toString().trim());
  }

  @Test
  void twoNonBlankDocumentShouldWork() {
    String[] multipleNonBlank = new String[2];
    multipleNonBlank[0] = "the brown fox jumped over the brown dog";
    multipleNonBlank[1] = "the lazy brown dog sat in the corner";
    SearchApplication.main(multipleNonBlank);
    assertNotEquals("Usage: java -jar target/search-0.0.1-SNAPSHOT.jar " +
        "\"doc1\" \"doc2\" ... (must contain several non-white documents)",
      captor.toString().trim());
  }

}
