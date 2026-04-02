package AST;


/*
    Expressions evaluate to return a value but don't change state.
    */
sealed interface expression permits operation, assignment {}
