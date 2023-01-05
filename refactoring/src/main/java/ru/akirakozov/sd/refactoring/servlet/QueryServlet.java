package ru.akirakozov.sd.refactoring.servlet;

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
          QueryOutput.formatResponse(response, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "<h1>Product with max price: </h1>\r\n", false);
        else if ("min".equals(command))
          QueryOutput.formatResponse(response, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "<h1>Product with min price: </h1>\r\n", false);
        else if ("sum".equals(command))
          QueryOutput.formatResponse(response, "SELECT SUM(price) FROM PRODUCT", "Summary price: \r\n", true);
        else if ("count".equals(command))
          QueryOutput.formatResponse(response, "SELECT COUNT(*) FROM PRODUCT", "Number of products: \r\n", true);
        else response.getWriter().println("Unknown command: " + command);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
