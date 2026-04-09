package AST;


/*
    Expressions evaluate to return a value but don't change state.
    */
public sealed interface expression
        permits expression.number, expression.variable, expression.binary, expression.unary {

    record number(double value) implements expression {}
    record variable(String name) implements expression {}
    record binary(expression left, String op, expression right) implements expression {}
    record unary(String op, expression expr) implements expression {}
}
