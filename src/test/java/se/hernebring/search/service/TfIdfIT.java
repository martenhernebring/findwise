package se.hernebring.search.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import se.hernebring.search.exception.IllegalQueryException;
import se.hernebring.search.model.Term;
import se.hernebring.search.repository.TermRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TfIdfIT {

  @Autowired
  private TfIdfCalculator sorterTest;

  @Autowired
  private TfIdfEngine engineTest;

  @Autowired
  private TermRepository termRepositoryTest;

  private String[] texts;
  private List<String> expected;

  @BeforeEach
  void setUp() {
    texts = new String[]{"this is a a sample",
      "this is another another example example example"};
    sorterTest.index(texts);
    expected = new ArrayList<>();
  }

  @Test
  void searchForNonExistingWord() {
    assertThrows(IllegalQueryException.class, () -> engineTest.search("qwe"));
  }

  @Test
  void searchForSameTfIdfValue() {
    expected.add(texts[0]);
    expected.add(texts[1]);
    assertEquals(expected, engineTest.search("this"));
  }

  @Test
  void wikipediaExample() {
    Term term = termRepositoryTest.findByWord("example");
    var exampleTfIdf = term.getDocumentTfIdf().keySet().iterator().next();
    assertEquals("0.129", String.format(Locale.US, "%.3f", exampleTfIdf));
  }
}
