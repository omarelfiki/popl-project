import AST.*;
import AST.expression.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {
    @Test
    void testNumber() {
        environment env = environment.empty();
        double result = environment.eval(new expression.number(5), env);
        assertEquals(5.0, result);
    }
    
    @Test
    void testUnaryNegative() {
        environment env = environment.empty();
        double result = environment.eval(new expression.unary("-", new number(5.0)), env);
        assertEquals(-5.0, result);
    }

    @Test
    void testUnaryPositive() {
        environment env = environment.empty();
        double result = environment.eval(new expression.unary("+", new number(-5.0)), env);
        assertEquals(5.0, result);
    }

    @Test
    void testAdditionOp() {
        environment env = environment.empty();
        number left = new number(5);
        number right = new number(3);
        binary b = new expression.binary(left, "+", right);
        double result = environment.eval(b, env);
        assertEquals(8.0, result);
    }

    @Test
    void testSubtractionOp() {
        environment env = environment.empty();
        number left = new number(5);
        number right = new number(3);
        binary b = new expression.binary(left, "-", right);
        double result = environment.eval(b, env);
        assertEquals(2.0, result);
    }

    @Test
    void testDivisionOp() {
        environment env = environment.empty();
        number left = new number(6);
        number right = new number(3);
        binary b = new expression.binary(left, "/", right);
        double result = environment.eval(b, env);
        assertEquals(2, result);
    }
    
    @Test
    void testMultiplicationOp() {
        environment env = environment.empty();
        number left = new number(5);
        number right = new number(3);
        binary b = new expression.binary(left, "*", right);
        double result = environment.eval(b, env);
        assertEquals(15, result);
    }
    
    @Test
    void testPowerOp() {
        environment env = environment.empty();
        number left = new number(5);
        number right = new number(3);
        binary b = new expression.binary(left, "**", right);
        double result = environment.eval(b, env);
        assertEquals(125, result);
    }
    
    @Test
    void testNaturalFold() {
        environment env = environment.empty();
        expression e = new expression.binary(new expression.number(5), "-", new expression.number(3));
        assertEquals(2, 
            environment.fold(e,
                        x -> x,
                        env::lookup,
                        (op, value) -> op.equals("-") ? -value : value,
                        (left, op, right) -> switch (op) {
                            case "+" -> left + right;
                            case "-" -> left - right;
                            case "*" -> left * right;
                            case "/" -> left / right;
                            default -> throw new IllegalArgumentException("unknown binary operator");
                        }
                )
        );
    }
}
