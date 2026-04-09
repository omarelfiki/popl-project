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
    void testBinaryOp(){
        environment env = environment.empty();
        number left = new number(5);
        number right = new number(3);
        binary b = new expression.binary(left, "+", right);
        double result = environment.eval(b, env);
        assertEquals(8.0, result);
    }

}
