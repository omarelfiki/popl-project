package examples;

import java.util.List;
import lexer.*;

public class LexerExample {
    public static void main(String[] args) {
        String source = "x = 2 + 3 ** 2";
        lexer lexer = new lexer(source);
        List<token> tokens = lexer.scanTokens();

        System.out.println("Source:");
        System.out.println(source);
        System.out.println();
        System.out.println("Tokens:");

        for (token token : tokens) {
            System.out.println(token.type() + " \"" + token.lexeme() + "\" at " + token.position());
        }
    }
}
