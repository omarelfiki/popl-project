package AST;

/* Statements execute to (maybe) change state and may or may not return a value */
public sealed interface statement permits statement.assign {
    
    public record assign(String var, expression expr) implements statement {}    
}