import net.minidev.json.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import pack.http.UrlCreator;
import pack.http.UrlRequest;
import pack.unpack.UnpackJSON;

import java.io.IOException;
import java.net.URL;

public class Tests {
  private final UrlCreator urlCreator = new UrlCreator();
  private final String[] fields = {"response", "total_count"};

  @Test
  public void wrongUrl() throws IOException, ParseException {
      URL res = urlCreator.createURL("covid", "a", "b");
      Object result = UrlRequest.request(res, fields);
      Assert.assertEquals(null, result);
  }

  @Test
  public void parseOk() throws ParseException {
    int result = (int) UnpackJSON.get("{'response': {'total_count': 10}}", fields);
    Assert.assertEquals(10, result);
  }

  @Test(expected = ClassCastException.class)
  public void parseError() throws ParseException {
    UnpackJSON.get("aaa", fields);
  }
}
