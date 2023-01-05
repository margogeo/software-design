package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.databases.DatabaseQuery;
import ru.akirakozov.sd.refactoring.utils.QueryOutput;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command))
          QueryOutput.formatResponse(response, DatabaseQuery.dbQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", false, false), "<h1>Product with max price: </h1>\r\n");
        else if ("min".equals(command))
          QueryOutput.formatResponse(response, DatabaseQuery.dbQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", false, false), "<h1>Product with min price: </h1>\r\n");
        else if ("sum".equals(command))
          QueryOutput.formatResponse(response, DatabaseQuery.dbQuery("SELECT SUM(price) FROM PRODUCT", true, false), "Summary price: \r\n");
        else if ("count".equals(command))
          QueryOutput.formatResponse(response, DatabaseQuery.dbQuery("SELECT COUNT(*) FROM PRODUCT", true, false), "Number of products: \r\n");
        else response.getWriter().println("Unknown command: " + command);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
