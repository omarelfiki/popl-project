import AST.*;
import AST.expression.*;
import AST.statement.*;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class environment {
    private final Map<String, Double> bindings;

    public environment(Map<String, Double> bindings) {
        this.bindings = bindings;
    }

    public double lookup(String name) {
        Double value = bindings.get(name);
        if (value == null) {
            throw new IllegalArgumentException("unbound variable: " + name);
        }
        return value;
    }

    public Map<String, Double> getBindings() {
        return this.bindings;
    }

    public static environment empty() {
        return new environment(Map.of());
    }

    public static environment exec(statement s, environment env) {
        return switch (s) {
            case assign a -> {
                double value = environment.eval(a.expr(), env);
                Map<String, Double> newBindings = env.getBindings();
                newBindings.put(a.var(), value);

                yield new environment(newBindings);
            }
            default -> throw new IllegalArgumentException("undefined statement");
        };
    }

    public static double eval(expression e, environment env) {
        /* Method recursively calls itself to evaluate the expression */
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
                    default -> throw new IllegalArgumentException("unknown binary operator");
                };
            }
        };
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

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
