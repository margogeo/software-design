package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;

import java.util.ArrayDeque;
import java.util.Deque;

import static token.OperationToken.OperationType.*;

public class CalcVisitor implements TokenVisitor {

    private final Deque<Integer> tokenDeque = new ArrayDeque<>();
    @Override
    public void visit(NumberToken numberToken) {
        tokenDeque.addLast(numberToken.number);
    }

    @Override
    public void visit(BraceToken braceToken) throws Exception {
        throw new Exception("No braces in the notation");
    }

    @Override
    public void visit(OperationToken operationToken) throws Exception {
        if (tokenDeque.size() < 2)
            throw new Exception("not enough tokens");

        int first = tokenDeque.removeLast(), second = tokenDeque.removeLast(), result;
        OperationToken.OperationType op = operationToken.type;
        if (op == ADD)
            result = first + second;
        else if (op == SUBTRACT)
            result = second - first;
        else if (op == MULTIPLY)
            result = first * second;
        else if (op == DIVIDE)
            result = second / first;
        else
            return;
        tokenDeque.addLast(result);
    }

    public int getResult() throws Exception {
        if (tokenDeque.size() == 1)
            return tokenDeque.getLast();
        throw new Exception("tokens not in the notation");
    }
}
