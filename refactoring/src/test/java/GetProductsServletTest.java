import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsServletTest extends InitTest {
    @Test
    void testGet() throws IOException {
      String response = new Scanner((new URL("http://localhost:8081/get-products")).openStream()).useDelimiter("\\A").next().trim();
      assertEquals("<html><body>\r\n</body></html>", response);
    }
}
