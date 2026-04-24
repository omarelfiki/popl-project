package interpreter;

import AST.expression;
import AST.statement;
import java.util.List;

public class interpreter {

    /**
     * Evaluates an expression in an empty environment.
     *
     * @param expr expression to evaluate
     * @return the numeric result of the expression
     */
    public double eval(expression expr) {
        return eval(expr, environment.empty());
    }

    /**
     * Evaluates an expression in the provided environment.
     *
     * @param expr expression to evaluate
     * @param env environment used for variable lookup
     * @return the numeric result of the expression
     */
    public double eval(expression expr, environment env) {
        return environment.eval(expr, env);
    }

    /**
     * Executes one statement in an empty environment.
     *
     * @param stmt statement to execute
     * @return the environment after executing the statement
     */
    public environment exec(statement stmt) {
        return exec(stmt, environment.empty());
    }

    /**
     * Executes one statement in the provided environment.
     *
     * @param stmt statement to execute
     * @param env starting environment
     * @return the environment after executing the statement
     */
    public environment exec(statement stmt, environment env) {
        return environment.exec(stmt, env);
    }

    /**
     * Executes a list of statements from an empty environment.
     *
     * @param program statements to execute in order
     * @return the final environment after the program finishes
     */
    public environment execProgram(List<statement> program) {
        return execProgram(program, environment.empty());
    }

    /**
     * Executes a list of statements from the provided environment.
     *
     * @param program statements to execute in order
     * @param initialEnv starting environment
     * @return the final environment after the program finishes
     */
    public environment execProgram(List<statement> program, environment initialEnv) {
        environment currentEnv = initialEnv;
        for (statement stmt : program) {
            currentEnv = exec(stmt, currentEnv);
        }
        return currentEnv;
    }
}
