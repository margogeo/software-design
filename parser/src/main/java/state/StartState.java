package state;

import token.*;
import static token.OperationToken.OperationType.*;

public class StartState extends State {
    StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }
    @Override
    public void nextState() {
        if (getNextCharacter() == Integer.MIN_VALUE) {
            if (!readNextCharacter()) {
                return;
            }
        }
        if (getNextCharacter() == -1) {
            setTokenizerState(new ErrorState(super.tokenizer));
            return;
        }
        char character = (char) getNextCharacter();
        if (Character.isDigit(character)) {
            setTokenizerState(new NumberState(super.tokenizer));
            return;
        }
        if (!Character.isWhitespace(character)) {
            Token set = null;
            if (character == '(')
                set = new BraceToken("left");
            if (character == ')')
                set = new BraceToken("right");
            if (character == '+')
                set = new OperationToken(ADD);
            if (character == '-')
                set = new OperationToken(SUBTRACT);
            if (character == '*')
                set = new OperationToken(MULTIPLY);
            if (character == '/')
                set = new OperationToken(DIVIDE);
            if (set != null)
                setToken(set);
        }
        if (character == '\n') {
            setTokenizerState(new EndState(super.tokenizer));
            return;
        }
        readNextCharacter();
    }

    private boolean readNextCharacter() {
        try {
            next();
            return true;
        } catch (Exception e) {
            setTokenizerState(new ErrorState(super.tokenizer));
            return false;
        }
    }
}
