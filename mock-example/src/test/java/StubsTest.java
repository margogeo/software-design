import com.xebialabs.restito.server.StubServer;
import net.minidev.json.parser.ParseException;
import org.glassfish.grizzly.http.Method;
import org.junit.Assert;
import org.junit.Test;
import pack.http.UrlCreator;
import pack.http.UrlRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;

public class StubsTest {
  private static final int PORT = 32453;
  private final UrlCreator urlCreator = new UrlCreator();

  @Test
  public void urlText() {
    withStubServer(PORT, s -> {
      whenHttp(s)
        .match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
        .then(stringContent("{'response':{'count':7,'items':[],'total_count':7}}"));

      URL result = urlCreator.createURL("http","covid", "localhost", PORT,"", "");
      URL expected = null;
      try {
        expected = new URL("http://localhost:32453/method/newsfeed.search?q=%23covid&v=5.131&access_token=vk1.a.56mRWTjx-hgAyGDwrAeiez_MxXpaPrysHhwzZs0VaR7L0ga5ik7_m0l-ODdO0QX5iMUbMeZDHYAydEf2HYdtiS2W1cdIDRLDF9CydGkWA-xx6AlnARlrDWuTSlAzuDjZJRbaFLdMHwJX34QSbvqjn9sYIzMNYg8s1TvIuZG14yjA_em6MTl_ZlLulqUEKU_nv7hMWQzWoL0Q79p9noDSow&count=0&start_time=&end_time=");
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
      Assert.assertEquals(expected, result);
    });
  }

  @Test
  public void invalidRequest() {
    withStubServer(PORT, s -> {
      whenHttp(s)
        .match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
        .then(stringContent("{'error':{'error_code':100,'error_msg':'One of the parameters specified was missing or invalid: start_time not integer','request_params':[{'key':'q','value':'#covid'},{'key':'v','value':'5.131'},{'key':'count','value':'0'},{'key':'start_time','value':'a'},{'key':'end_time','value': b'},{'key':'method','value':'newsfeed.search'},{'key':'oauth','value':'1'}]}}"));

      String[] fields = {"response", "total_count"};
      try {
        Assert.assertEquals(null, UrlRequest.request(urlCreator.createURL("http","covid", "localhost", PORT,"", ""), fields));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  public void successRequest() {
    withStubServer(PORT, s -> {
      whenHttp(s)
        .match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
        .then(stringContent("{'response':{'count':8,'items':[],'total_count':8}}"));

      String[] fields = {"response", "total_count"};
      try {
        Assert.assertEquals(8, UrlRequest.request(urlCreator.createURL("http","covid", "localhost", PORT,"", ""), fields));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    });
  }

  private void withStubServer(int port, Consumer<StubServer> callback) {
    StubServer stubServer = null;
    try {
      stubServer = new StubServer(port).run();
      callback.accept(stubServer);
    } finally {
      if (stubServer != null) {
        stubServer.stop();
      }
    }
  }
}
