package visitor;

import token.*;
import java.io.*;
import static token.OperationToken.OperationType.*;

public class PrintVisitor implements TokenVisitor {

    private final PrintWriter printWriter;

    public PrintVisitor(OutputStream output) {
        this.printWriter = new PrintWriter(output);
    }

    @Override
    public void visit(NumberToken num) {
        printWriter.print(num.number + " ");
    }

    @Override
    public void visit(BraceToken brace) throws Exception {
        throw new Exception("No braces in the notation");
    }

    @Override
    public void visit(OperationToken operationToken) {
        String op = "";
        OperationToken.OperationType type = operationToken.type;
        if (type == ADD)
            op = "+";
        if (type == SUBTRACT)
            op = "-";
        if (type == MULTIPLY)
            op = "*";
        if (type == DIVIDE)
            op = "/";
        printWriter.print(op + " ");
    }

    public void flush() {
        printWriter.println();
        printWriter.flush();
    }
}
