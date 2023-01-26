package state;

import token.Token;

import java.io.*;
import java.util.*;

public class Tokenizer {

    private final Reader reader;
    private final List<Token> tokensList = new ArrayList<>();
    private State state = new StartState(this);
    private int nextCharacter = Integer.MIN_VALUE;
    private boolean finished = false;

    public Tokenizer(InputStream input) {
        this.reader = new InputStreamReader(input);
    }

    void setToken(Token token) {
        tokensList.add(token);
    }
    void setTokenizerState(State state) {
        this.state = state;
    }
    void exit() {
        this.finished = true;
    }

    public List<Token> getTokensList() throws Exception {
        while (!finished) {
            state.nextState();
            if (state instanceof ErrorState)
                throw new Exception("error in tokenizer");
        }
        return tokensList;
    }
    void next() throws Exception {
        nextCharacter = reader.read();
    }
    int getNextCharacter() {
        return nextCharacter;
    }
}
