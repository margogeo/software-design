import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class InitTest {

  static void query(String sql) throws SQLException {
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      Statement stmt = c.createStatement();

      stmt.executeUpdate(sql);
      stmt.close();
    }
  }

    @BeforeAll
    static void init() throws Exception {
          query("CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)");

            Server server = new Server(8081);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);

            context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
            context.addServlet(new ServletHolder(new GetProductsServlet()),"/get-products");
            context.addServlet(new ServletHolder(new QueryServlet()),"/query");

            server.start();
    }

    @AfterEach
    void clear() throws Exception {
      query("DELETE FROM PRODUCT");
    }
}
