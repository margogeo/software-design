import token.Token;
import state.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParseVisitor;
import visitor.PrintVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Token> tokens = (new Tokenizer(System.in)).getTokensList();
        PrintVisitor printVisitor = new PrintVisitor(System.out);
        CalcVisitor calcVisitor = new CalcVisitor();
        ParseVisitor parseVisitor = new ParseVisitor();
        for (Token token : tokens) {
            token.accept(parseVisitor);
        }

        List<Token> notation = parseVisitor.getNotation();
        for (Token token : notation) {
            token.accept(printVisitor);
            token.accept(calcVisitor);
        }
        printVisitor.flush();
        System.out.print(calcVisitor.getResult());
    }
}
