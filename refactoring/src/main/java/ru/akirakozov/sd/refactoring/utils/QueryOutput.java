package ru.akirakozov.sd.refactoring.utils;

import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryOutput {
  public static void formatResponse(HttpServletResponse response, String query, String additionalInfo, boolean isCountRequired) {
    try {
      Statement stmt = DriverManager.getConnection("jdbc:sqlite:test.db").createStatement();
      ResultSet rs = stmt.executeQuery(query);
      StringBuilder output = new StringBuilder();
      output.append("<html><body>\r\n" + additionalInfo);
      while (rs.next()) {
        if (isCountRequired)
          output.append(rs.getInt(1) + "\r\n");
        else
          output.append(rs.getString("name") + "\t" + rs.getInt("price") + "</br>\r\n");
      }
      output.append("</body></html>\r\n");
      response.getWriter().println(output.toString());

      rs.close();
      stmt.close();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
}
