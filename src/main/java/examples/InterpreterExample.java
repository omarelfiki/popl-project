package examples;

import AST.expression.*;
import AST.statement.*;
import interpreter.*;
import java.util.List;

public class InterpreterExample {
    public static void main(String[] args) {
        interpreter interpreter = new interpreter();

        double evaluated = interpreter.eval(
                new binary(new number(2), "+", new binary(new number(3), "*", new number(4)))
        );
        System.out.println("evaluate: 2 + 3 * 4 = " + evaluated);

        environment env = interpreter.exec(new assign("x", new number(2)));
        System.out.println("execute: x = " + env.lookup("x"));

        env = interpreter.execProgram(List.of(
                new assign("x", new number(2)),
                new assign("y", new binary(new variable("x"), "**", new number(3))),
                new assign("z", new binary(new variable("y"), "+", new number(10)))
        ), env);

        System.out.println("execute program:");
        System.out.println("x = " + env.lookup("x"));
        System.out.println("y = " + env.lookup("y"));
        System.out.println("z = " + env.lookup("z"));
    }
}
