package AST;

/**
 * Defines the type structure of the interpreter
 * Expressions evaluate to return a value but don't change the program's state.
 * This sealed interface restricts valid expression forms to:
 * {@link expression.number}, {@link expression.variable},
 * {@link expression.binary}, and {@link expression.unary}.
 *
 *  <p>The recursive fields in {@code binary} and {@code unary} allow nested
 *  expression trees (for example: {@code -(x + 3)}).
 */
public sealed interface expression permits expression.number, expression.variable, expression.binary, expression.unary {

    record number(double value) implements expression {}
    record variable(String name) implements expression {}

    // Binary and Unary records self-reference the expression interface to allow for nested expressions
    record binary(expression left, String op, expression right) implements expression {}
    record unary(String op, expression expr) implements expression {}
}