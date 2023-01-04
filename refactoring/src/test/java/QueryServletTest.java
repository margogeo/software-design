import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryServletTest extends InitTest {

  private String sendQueryRequest(String command) throws IOException {
    return new Scanner((new URL("http://localhost:8081/query?command=" + command)).openStream()).useDelimiter("\\A").next().trim();
  }

  private int indexRandom(int index) {
    return (int)Math.round(Math.random() * index);
  }

  @Test
  void testSum() throws IOException, SQLException {
    int sum = 0, count = indexRandom(3);
    for (int i = 0; i < count; i++) {
      int price = indexRandom(3);
      sum += price;
      InitTest.query("INSERT INTO PRODUCT(NAME, PRICE) VALUES (\"" + i + "\", " + price + ")");
    }
    assertEquals("<html><body>\r\nSummary price: \r\n" + sum + "\r\n</body></html>", sendQueryRequest("sum"));
  }

  @Test
  void testCount() throws IOException, SQLException {
    int count = indexRandom(3);
    for (int i = 0; i < count; i++) {
      InitTest.query("INSERT INTO PRODUCT(NAME, PRICE) VALUES (\"" + i + "\", " + indexRandom(3) + ")");
    }
    assertEquals("<html><body>\r\nNumber of products: \r\n" + count + "\r\n</body></html>", sendQueryRequest("count"));
  }

  @Test
  void testMax() throws IOException, SQLException {
    int maxProduct = 0, maxPrice = Integer.MIN_VALUE, count = indexRandom(3);
    for (int i = 0; i < count; i++) {
      int price = indexRandom(3);
      if (maxPrice < price) {
        maxProduct = i;
        maxPrice = price;
      }
      InitTest.query("INSERT INTO PRODUCT(NAME, PRICE) VALUES (\"" + i + "\", " + price + ")");
    }
    String expectedResult = count == 0 ? "" : maxProduct + "\t" + maxPrice  + "</br>\r\n";
    assertEquals("<html><body>\r\n<h1>Product with max price: </h1>\r\n" + expectedResult + "</body></html>", sendQueryRequest("max"));
  }

  @Test
  void testMin() throws IOException, SQLException {
    int minProduct = 0, minPrice = Integer.MAX_VALUE, count = indexRandom(3);
    for (int i = 0; i < count; i++) {
      int price = indexRandom(3);
      if (minPrice > price) {
        minProduct = i;
        minPrice = price;
      }
      InitTest.query("INSERT INTO PRODUCT(NAME, PRICE) VALUES (\"" + i + "\", " + price + ")");
    }
    String expectedResult = count == 0 ? "" : minProduct + "\t" + minPrice + "</br>\r\n";
    assertEquals("<html><body>\r\n<h1>Product with min price: </h1>\r\n" + expectedResult + "</body></html>", sendQueryRequest("min"));
  }
}
