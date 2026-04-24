import java.util.List;

import lexer.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void testScansAssignmentExpression() {
        lexer lexer = new lexer("x = 2 + 3");

        List<token> tokens = lexer.scanTokens();

        assertEquals(List.of(
                tokenType.IDENTIFIER,
                tokenType.ASSIGN,
                tokenType.NUMBER,
                tokenType.PLUS,
                tokenType.NUMBER,
                tokenType.EOF
        ), tokenTypes(tokens));
        assertEquals("x", tokens.get(0).lexeme());
        assertEquals("2", tokens.get(2).lexeme());
        assertEquals("3", tokens.get(4).lexeme());
    }

    @Test
    void testScansPowerBeforeStar() {
        lexer lexer = new lexer("2 ** 3 * 4");

        List<token> tokens = lexer.scanTokens();

        assertEquals(List.of(
                tokenType.NUMBER,
                tokenType.POWER,
                tokenType.NUMBER,
                tokenType.STAR,
                tokenType.NUMBER,
                tokenType.EOF
        ), tokenTypes(tokens));
    }

    @Test
    void testScansDecimalsIdentifiersParenthesesAndNewlines() {
        lexer lexer = new lexer("total_1 = -(2.5)\n");

        List<token> tokens = lexer.scanTokens();

        assertEquals(List.of(
                tokenType.IDENTIFIER,
                tokenType.ASSIGN,
                tokenType.MINUS,
                tokenType.LEFT_PAREN,
                tokenType.NUMBER,
                tokenType.RIGHT_PAREN,
                tokenType.NEWLINE,
                tokenType.EOF
        ), tokenTypes(tokens));
        assertEquals("total_1", tokens.get(0).lexeme());
        assertEquals("2.5", tokens.get(4).lexeme());
    }

    @Test
    void testRejectsUnexpectedCharacter() {
        lexer lexer = new lexer("x @ 2");

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, lexer::scanTokens);

        assertEquals("unexpected character: @", exception.getMessage());
    }

    private List<tokenType> tokenTypes(List<token> tokens) {
        return tokens.stream().map(token::type).toList();
    }
}
