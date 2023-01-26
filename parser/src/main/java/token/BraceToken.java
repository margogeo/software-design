package token;

import visitor.TokenVisitor;

public class BraceToken implements Token {

    public String brace;
    public BraceToken(String brace) {
        this.brace = brace;
    }

    @Override
    public void accept(TokenVisitor tokenVisitor) throws Exception {
        tokenVisitor.visit(this);
    }
}
