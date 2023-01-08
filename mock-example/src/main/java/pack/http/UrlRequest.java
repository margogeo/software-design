package pack.http;

import net.minidev.json.parser.ParseException;
import pack.unpack.UnpackJSON;

import java.io.*;
import java.net.*;

public class UrlRequest {
    public static Object request(URL finalURL, String[] fields) throws IOException, ParseException {
      HttpURLConnection connection = (HttpURLConnection) finalURL.openConnection();
      connection.setRequestMethod("GET");
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        BufferedReader str = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = str.readLine()) != null) {
          response.append(inputLine);
        }
        return UnpackJSON.get(response.toString(), fields);
      } else {
          return ("invalid get request:" + connection.getResponseMessage());
      }
    }
}
