package pack.http;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlCreator {

  public static URL createURL(String protocol, String hashTag, String host, int port, String startTimeSec, String endTimeSec) {
    String access_token = "vk1.a.56mRWTjx-hgAyGDwrAeiez_MxXpaPrysHhwzZs0VaR7L0ga5ik7_m0l-ODdO0QX5iMUbMeZDHYAydEf2HYdtiS2W1cdIDRLDF9CydGkWA-xx6AlnARlrDWuTSlAzuDjZJRbaFLdMHwJX34QSbvqjn9sYIzMNYg8s1TvIuZG14yjA_em6MTl_ZlLulqUEKU_nv7hMWQzWoL0Q79p9noDSow";
    String urlString = protocol + "://" + host + ":" + port + "/method/newsfeed.search" +
          "?q=%23" + hashTag + "&v=5.131&access_token=" + access_token +
          "&count=0&start_time=" + startTimeSec + "&end_time=" + endTimeSec;
      try {
        return new URL(urlString);
      } catch (MalformedURLException e) {
        throw new RuntimeException("wrong" + urlString);
      }
    }
  public static URL createURL(String hashTag, String startTimeSec, String endTimeSec) {
    String access_token = "vk1.a.56mRWTjx-hgAyGDwrAeiez_MxXpaPrysHhwzZs0VaR7L0ga5ik7_m0l-ODdO0QX5iMUbMeZDHYAydEf2HYdtiS2W1cdIDRLDF9CydGkWA-xx6AlnARlrDWuTSlAzuDjZJRbaFLdMHwJX34QSbvqjn9sYIzMNYg8s1TvIuZG14yjA_em6MTl_ZlLulqUEKU_nv7hMWQzWoL0Q79p9noDSow";
    String urlString = "https://api.vk.com:443/method/newsfeed.search" +
      "?q=%23" + hashTag + "&v=5.131&access_token=" + access_token +
      "&count=0&start_time=" + startTimeSec + "&end_time=" + endTimeSec;
    try {
      return new URL(urlString);
    } catch (MalformedURLException e) {
      throw new RuntimeException("wrong" + urlString);
    }
  }
}

