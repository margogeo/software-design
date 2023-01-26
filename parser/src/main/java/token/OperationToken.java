package token;

import visitor.TokenVisitor;

public class OperationToken implements Token {

    public final OperationType type;
    public OperationToken(OperationType operationType) {
        this.type = operationType;
    }

    @Override
    public void accept(TokenVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    public enum OperationType {
        ADD(1),
        SUBTRACT(1),
        MULTIPLY(0),
        DIVIDE(0);

        public final int priority;
        OperationType(int priority) {
            this.priority = priority;
        }
    }
}
