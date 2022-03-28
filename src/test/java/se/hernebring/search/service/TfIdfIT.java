package se.hernebring.search.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.Locale;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TfIdfIT {

  @Autowired
  private TfIdfCalculator sorterTest;

  @Autowired
  private TfIdfEngine engineTest;

  private String[] texts;
  private TreeMap<Double, String> expected;

  @BeforeEach
  void setUp() {
    texts = new String[]{"this is a a sample",
      "this is another another example example example"};
    sorterTest.index(texts);
    expected = new TreeMap<>(Collections.reverseOrder());
  }

  @Test
  void searchForSameTfIdfValue() {
    expected.put(-1.0E-18, texts[1]);
    expected.put(0.0, texts[0]);
    assertEquals(expected, engineTest.search("this"));
  }

  @Test
  void wikipediaExample() {
    Double exampleKey = engineTest.search("example").firstKey();
    assertEquals("0.129", String.format(Locale.US, "%.3f", exampleKey));
  }
}
