import net.minidev.json.parser.ParseException;
import pack.http.*;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
      Scanner in = new Scanner(System.in);
      String hashTag = in.next();
      if (hashTag.charAt(0) == '#')
        hashTag = hashTag.substring(1);
      int timePeriod = in.nextInt();
      in.close();
      int[] result = new int[timePeriod];
      for (int i = 0; i < timePeriod; i++) {
        long startTime = (Instant.now().getEpochSecond() - (i + 1) * 3600);
        long endTime = (Instant.now().getEpochSecond() - i * 3600);
        URL finalURL = UrlCreator.createURL( hashTag, startTime + "", endTime + "");
        String[] fields = {"response", "total_count"};
        Object response = UrlRequest.request(finalURL, fields);
        if (response != null) {
          result[i] = (int) response;
          System.out.print(result[i] + " ");
        } else {
          System.out.println("invalid response");
        }
      }
      System.out.println("Put the values in data values field, get a diagram: https://www.rapidtables.com/tools/bar-graph.html");
  }
}
