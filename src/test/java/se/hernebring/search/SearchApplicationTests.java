package se.hernebring.search;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
  void emptyArgumentIsIllegal() {
    String[] empty = new String[0];
    var ex = assertThrows(IllegalArgumentException.class,
      () -> SearchApplication.main(empty));
    assertEquals("Args must not be empty", ex.getMessage());
  }

}
