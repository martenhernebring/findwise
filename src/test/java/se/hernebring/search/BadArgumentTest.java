package se.hernebring.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {SearchApplication.class, Argument.class, CommandLineRunner.class})
class BadArgumentTest {

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
  void emptyArgumentShouldPrintInstructions() {
    String[] empty = new String[0];
    SearchApplication.main(empty);
    assertEquals(Argument.USAGE, captor.toString().trim());
  }

  @Test
  void oneDocumentOnlyShouldPrintInstructions() {
    String[] justOneDoc = new String[1];
    justOneDoc[0] = "the brown fox jumped over the brown dog";
    SearchApplication.main(justOneDoc);
    assertEquals(Argument.USAGE + Argument.MULTIPLE, captor.toString().trim());
  }

  @Test
  void oneNonBlankDocumentOnlyShouldPrintInstructions() {
    String[] justOneNonWhite = new String[2];
    justOneNonWhite[0] = "the brown fox jumped over the brown dog";
    justOneNonWhite[1] = " ";
    SearchApplication.main(justOneNonWhite);
    assertEquals(Argument.USAGE + Argument.MULTIPLE, captor.toString().trim());
  }

}
