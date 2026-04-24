package lexer;

/**
 * A single lexical unit found in the source code.
 *
 * @param type category of token
 * @param lexeme exact source text matched for this token
 * @param position zero-based character position where the token starts
 */
public record token(tokenType type, String lexeme, int position) {
}
