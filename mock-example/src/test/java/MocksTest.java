import net.minidev.json.parser.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pack.http.UrlCreator;
import pack.http.UrlRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

import static org.mockito.Mockito.when;

public class MocksTest {
  private String[] fields = {"response", "total_count"};

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  public void get(String hashTag, String response, int num, long start, long end) throws IOException, ParseException {
    URL UrlMock = Mockito.mock(URL.class);
    HttpURLConnection HttpURLConnectionlMock = Mockito.mock(HttpURLConnection.class);
    when(HttpURLConnectionlMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

    Mockito.doReturn(new ByteArrayInputStream("{'response':{'count':0,'items':[],'suggested_queries':['covid','covid 19','covid - 19','тест на covid','covid-19'],'total_count':10}}".getBytes())).when(HttpURLConnectionlMock).toString();

    when(UrlMock.openConnection()).thenReturn(HttpURLConnectionlMock);
    MockedStatic<UrlCreator> UrlCreatorMock = Mockito.mockStatic(UrlCreator.class);
    UrlCreatorMock.when(() -> UrlCreator.createURL(Mockito.any(), Mockito.any(), Mockito.any()))
      .thenReturn(UrlMock);

    when(UrlRequest.request(UrlMock, fields)).thenReturn(response);

    Assert.assertEquals(num, UrlRequest.request(UrlCreator.createURL(hashTag, (start + ""), (end + "")), fields));
  }

  @Test
  public void getCompany() throws IOException, ParseException {
    long end = Instant.now().getEpochSecond();
    long start = (end - 3600);
    get(
      "news",
      "{'response': {'total_count': 10}}",
      10,
      start,
      end
    );
  }
}
