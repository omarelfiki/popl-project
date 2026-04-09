import AST.expression.*;
import AST.*;
import java.util.Map;

public class environment {
    private final Map<String, Double> bindings;

    public environment(Map<String, Double> bindings) {
        this.bindings = bindings;
    }

    public static environment empty() {
        return new environment(Map.of());
    }

    public double lookup(String name) {
        Double value = bindings.get(name);
        if (value == null) {
            throw new IllegalArgumentException("unbound variable: " + name);
        }
        return value;
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
}
