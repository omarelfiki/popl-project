import AST.*;
import AST.expression.*;
import AST.statement.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class environment {

    private final Map<String, Double> bindings;

    /**
     * Creates an environment with the provided variable bindings.
     *
     * @param bindings map from variable names to numeric values
     */
    public environment(Map<String, Double> bindings) {
        this.bindings = bindings;
    }

    /**
     * Looks up the value currently bound to a variable name.
     *
     * @param name variable name to find
     * @return the variable's numeric value
     * @throws IllegalArgumentException if the variable is not bound
     */
    public double lookup(String name) {
        Double value = bindings.get(name);
        if (value == null) {
            throw new IllegalArgumentException("unbound variable: " + name);
        }
        return value;
    }

    /**
     * Returns the map of variable bindings stored in this environment.
     *
     * @return variable bindings for this environment
     */
    public Map<String, Double> getBindings() {
        return this.bindings;
    }

    /**
     * Creates an empty environment with no variable bindings.
     *
     * @return an empty environment
     */
    public static environment empty() {
        return new environment(new HashMap<>());
    }

    /**
     * Executes one statement in the given environment.
     *
     * @param s statement to execute
     * @param env environment used while executing the statement
     * @return a new environment containing the statement's effects
     */
    public static environment exec(statement s, environment env) {
        return switch (s) {
            case assign a -> {
                double value = environment.eval(a.expr(), env);
                Map<String, Double> newBindings = new HashMap<>(env.getBindings());
                newBindings.put(a.var(), value);

                yield new environment(newBindings);
            }
        };
    }

    /**
     * Evaluates an expression in the given environment.
     *
     * @param e expression to evaluate
     * @param env environment used for variable lookup
     * @return the numeric result of the expression
     * @throws IllegalArgumentException if an operator or variable is unknown
     */
    public static double eval(expression e, environment env) {
        return switch (e) {
            case number n -> n.value();
            case variable v -> env.lookup(v.name());
            case unary u -> switch (u.op()) {
                case "-" -> -eval(u.expr(), env);
                case "+" -> eval(u.expr(), env);
                default -> throw new IllegalArgumentException("unknown unary operator");
            };
            case binary b -> {
                double l = eval(b.left(), env);
                double r = eval(b.right(), env);
                yield switch (b.op()) {
                    case "+" -> l + r;
                    case "-" -> l - r;
                    case "*" -> l * r;
                    case "/" -> l / r;
                    case "**" -> Math.pow(l, r);
                    default -> throw new IllegalArgumentException("unknown binary operator");
                };
            }
        };
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    /**
     * Folds an expression tree by replacing each AST node with a caller-provided
     * operation.
     *
     * @param e expression tree to fold
     * @param onNumber handler for number expressions
     * @param onVariable handler for variable expressions
     * @param onUnary handler for unary expressions after folding the child
     * @param onBinary handler for binary expressions after folding both children
     * @return the folded result
     */
    static <R> R fold(
            expression e,
            Function<Double, R> onNumber,
            Function<String, R> onVariable,
            BiFunction<String, R, R> onUnary,
            TriFunction<R, String, R, R> onBinary) {
        return switch (e) {
            case number n   -> onNumber.apply(n.value());
            case variable v -> onVariable.apply(v.name());
            case unary u    -> onUnary.apply(
                    u.op(),
                    fold(u.expr(), onNumber, onVariable, onUnary, onBinary));
            case binary b   -> onBinary.apply(
                    fold(b.left(),  onNumber, onVariable, onUnary, onBinary),
                    b.op(),
                    fold(b.right(), onNumber, onVariable, onUnary, onBinary));
        };
    }
}
