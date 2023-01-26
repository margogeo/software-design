package state;

import token.NumberToken;

public class NumberState extends State {

    StringBuilder builder = new StringBuilder();
    NumberState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextState() {
        char ch = (char) getNextCharacter();
        if (Character.isDigit(ch)) {
            builder.append(ch);
            try {
                next();
            } catch (Exception e) {
                setTokenizerState(new ErrorState(super.tokenizer));
            }
        } else {
            setToken(new NumberToken(Integer.parseInt(builder.toString())));
            setTokenizerState(new StartState(super.tokenizer));
        }
    }
}
