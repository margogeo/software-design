package ru.akirakozov.sd.refactoring.databases;

import java.sql.*;

public class DatabaseQuery {
  public static String dbQuery(String query, boolean isCountRequired, boolean isUpdate) {
    try {
      Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement stmt = c.createStatement();
      StringBuilder output = new StringBuilder();
      if (isUpdate) {
        stmt.executeUpdate(query);
      } else {
        ResultSet result = stmt.executeQuery(query);
        while (result.next()) {
          if (isCountRequired)
            output.append(result.getInt(1) + "\r\n");
          else
            output.append(result.getString("name") + "\t" + result.getInt("price") + "</br>\r\n");
        }
        result.close();
      }
      stmt.close();
      return output.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
