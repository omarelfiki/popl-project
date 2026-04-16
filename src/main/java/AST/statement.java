package AST;

/**
 * Root type for all statement nodes in the AST.
 *
 * <p>A statement represents an executable action that may
 * mutate the program state (for example, variable bindings).
 * This sealed interface currently allows only {@link statement.assign}.
 *
 */
public sealed interface statement permits statement.assign {

     record assign(String var, expression expr) implements statement {}
}

