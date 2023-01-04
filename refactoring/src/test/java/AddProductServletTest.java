import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddProductServletTest extends InitTest {
    @Test
    void testAdd() throws IOException {
      String response = new Scanner((new URL("http://localhost:8081/add-product?name=1&price=1")).openStream(), "UTF-8").useDelimiter("\\A").next().trim();
      assertEquals("OK", response);
    }
}
