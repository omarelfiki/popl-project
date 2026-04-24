package lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts raw source code into tokens.
 *
 * <p>The lexer recognizes numbers, identifiers, arithmetic operators,
 * assignment, parentheses, newlines, and an EOF marker. It does not build
 * syntax trees or evaluate code.
 */
public class lexer {
    private final String source;
    private final List<token> tokens = new ArrayList<>();
    private int current = 0;

    /**
     * Creates a lexer for the given source text.
     *
     * @param source program text to scan
     */
    public lexer(String source) {
        this.source = source;
    }

    /**
     * Converts source code into a list of tokens for the parser.
     *
     * @return tokens found in the source, ending with EOF
     * @throws IllegalArgumentException if the source contains an unsupported character
     */
    public List<token> scanTokens() {
        while (!isAtEnd()) {
            scanToken();
        }

        tokens.add(new token(tokenType.EOF, "", current));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case ' ', '\r', '\t' -> {
            }
            case '\n' -> addToken(tokenType.NEWLINE, "\n", current - 1);
            case '+' -> addToken(tokenType.PLUS, "+", current - 1);
            case '-' -> addToken(tokenType.MINUS, "-", current - 1);
            case '/' -> addToken(tokenType.SLASH, "/", current - 1);
            case '=' -> addToken(tokenType.ASSIGN, "=", current - 1);
            case '(' -> addToken(tokenType.LEFT_PAREN, "(", current - 1);
            case ')' -> addToken(tokenType.RIGHT_PAREN, ")", current - 1);
            case '*' -> {
                int start = current - 1;
                if (match()) {
                    addToken(tokenType.POWER, "**", start);
                } else {
                    addToken(tokenType.STAR, "*", start);
                }
            }
            default -> {
                if (isDigit(c)) {
                    number(current - 1);
                } else if (isIdentifierStart(c)) {
                    identifier(current - 1);
                } else {
                    throw new IllegalArgumentException("unexpected character: " + c);
                }
            }
        }
    }

    private void number(int start) {
        while (!isAtEnd() && isDigit(peek())) {
            advance();
        }

        if (!isAtEnd() && peek() == '.' && hasDigitAfterDecimal()) {
            advance();
            while (!isAtEnd() && isDigit(peek())) {
                advance();
            }
        }

        addToken(tokenType.NUMBER, source.substring(start, current), start);
    }

    private void identifier(int start) {
        while (!isAtEnd() && isIdentifierPart(peek())) {
            advance();
        }

        addToken(tokenType.IDENTIFIER, source.substring(start, current), start);
    }

    private void addToken(tokenType type, String lexeme, int position) {
        tokens.add(new token(type, lexeme, position));
    }

    private boolean match() {
        if (isAtEnd() || source.charAt(current) != '*') {
            return false;
        }

        current++;
        return true;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        return source.charAt(current);
    }

    private boolean hasDigitAfterDecimal() {
        return current + 1 < source.length() && isDigit(source.charAt(current + 1));
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isIdentifierStart(char c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isIdentifierPart(char c) {
        return isIdentifierStart(c) || isDigit(c);
    }
}
