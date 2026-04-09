package AST;

/* Statements execute to (maybe) change state and may or may not return a value */
public sealed interface statement permits statement.assign {
    
    // TODO: Discuss with Omar about how to structure this. The way it is right now leads to some quirky assignment concepts
    public record assign(String var, expression expr) implements statement {}    
}