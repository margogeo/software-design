package token;

import visitor.TokenVisitor;

import java.math.BigInteger;

public class NumberToken implements Token {

    public final int number;
    public NumberToken(int number) {
        this.number = number;
    }
    @Override
    public void accept(TokenVisitor tokenVisitor) {
        tokenVisitor.visit(this);
    }
}
