package state;

import token.Token;

public abstract class State {

    Tokenizer tokenizer;
    State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract void nextState();

    public void exit() {
        this.tokenizer.exit();
    }
    public void setTokenizerState(State tokenizerState) {
        this.tokenizer.setTokenizerState(tokenizerState);
    }

    public void setToken(Token token) {
        this.tokenizer.setToken(token);
    }

    public int getNextCharacter() {
        return this.tokenizer.getNextCharacter();
    }

    public void next() throws Exception {
        this.tokenizer.next();
    }
}
