import org.junit.jupiter.api.Test;
import static com.sun.org.apache.xpath.internal.XPathAPI.eval;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class InterpreterTest {
    @Test
    void testNumber(){
        Env env = Env.empty();
        double result = eval(new Num(5), env);
        assertEquals(5.0, result);
    }

}
