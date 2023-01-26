package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken numberToken);
    void visit(BraceToken braceToken) throws Exception;
    void visit(OperationToken operationToken) throws Exception;
}
