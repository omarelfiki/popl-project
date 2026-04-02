package AST;


/*
    Expressions evaluate to return a value but don't change state.
    */
sealed interface expression permits number, variable, binary, unary {}

record number(double variable) implements expression {}
record variable(String name) implements expression {}
record binary(expression left, String op, expression right) implements expression {}
record unary(String op, expression expr) implements expression {}
