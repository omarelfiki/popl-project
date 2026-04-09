package AST;

/*
    Statements execute to (maybe) change state and may or may not return a value
 */
sealed interface statement permits assign {}

record assign(String var, expression expr) implements statement {}
