package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.utils.QueryOutput;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        QueryOutput.formatResponse(response, "SELECT * FROM PRODUCT" , "", false);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
