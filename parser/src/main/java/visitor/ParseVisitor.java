package visitor;

import token.*;
import java.util.*;

public class ParseVisitor implements TokenVisitor {
    private final Deque<Token> tokenDeque = new ArrayDeque<>();
    private final List<Token> notation = new ArrayList<>();

    @Override
    public void visit(NumberToken numberToken) {
        notation.add(numberToken);
    }

    @Override
    public void visit(BraceToken brace) throws Exception {
        if (brace.brace.equals("left")) {
            tokenDeque.addLast(brace);
            return;
        }
        while (!tokenDeque.isEmpty()) {
            Token topToken = tokenDeque.removeLast();
            if (topToken instanceof BraceToken && ((BraceToken) topToken).brace.equals("left"))
                return;
            else
                notation.add(topToken);
        }
        throw new Exception("Not matching braces");
    }

    @Override
    public void visit(OperationToken operation) {
        int priority = operation.type.priority;
        if (!tokenDeque.isEmpty()) {
            Token top = tokenDeque.getLast();
            while (top instanceof OperationToken && priority >= ((OperationToken) top).type.priority) {
                notation.add(tokenDeque.removeLast());
                if (tokenDeque.isEmpty())
                    break;
                top = tokenDeque.getLast();
            }
        }
        tokenDeque.addLast(operation);
    }

    public List<Token> getNotation() throws Exception {
        while (!tokenDeque.isEmpty()) {
            Token top = tokenDeque.pollLast();
            if (!(top instanceof OperationToken))
                throw new Exception("Not matching braces");
            notation.add(top);
        }
        return new ArrayList<>(notation);
    }
}
