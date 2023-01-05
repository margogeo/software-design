package ru.akirakozov.sd.refactoring.utils;

import javax.servlet.http.HttpServletResponse;

public class QueryOutput {
  public static void formatResponse(HttpServletResponse response, String queryResult, String additionalInfo) {
    try {
      StringBuilder output = new StringBuilder();
      output.append("<html><body>\r\n" + additionalInfo + queryResult + "</body></html>\r\n");
      response.getWriter().println(output.toString());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
}
