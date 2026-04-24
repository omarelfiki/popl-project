package lexer;

/**
 * Token categories produced by the lexer.
 */
public enum tokenType {
    /** Numeric literal, such as {@code 12} or {@code 3.5}. */
    NUMBER,
    /** Variable name, such as {@code x} or {@code total_1}. */
    IDENTIFIER,
    /** Addition or unary plus operator: {@code +}. */
    PLUS,
    /** Subtraction or unary minus operator: {@code -}. */
    MINUS,
    /** Multiplication operator: {@code *}. */
    STAR,
    /** Division operator: {@code /}. */
    SLASH,
    /** Exponentiation operator: {@code **}. */
    POWER,
    /** Assignment operator: {@code =}. */
    ASSIGN,
    /** Left parenthesis: {@code (}. */
    LEFT_PAREN,
    /** Right parenthesis: {@code )}. */
    RIGHT_PAREN,
    /** Line break separating statements. */
    NEWLINE,
    /** End-of-file marker added after the final source token. */
    EOF
}
