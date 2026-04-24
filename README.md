# popl-project

A small Java interpreter project for basic mathematical expressions and
assignment statements.

The project currently has three main layers:

1. Lexer: turns source text into tokens.
2. AST: represents expressions and statements as Java records.
3. Interpreter: evaluates expressions and executes assignment statements.

The parser is the next missing layer. Right now, the lexer can scan source code,
and the interpreter can run manually constructed AST nodes.

## Requirements

- Java 25
- Maven

## Project Structure
```
src/main/java/
  AST/
    expression.java       Expression AST nodes
    statement.java        Statement AST nodes
  lexer/
    lexer.java            Source text scanner
    token.java            Token record
    tokenType.java        Token categories
  interpreter/
    environment.java      Variable bindings and evaluator
    interpreter.java      Public interpreter entry point
  examples/
    LexerExample.java
    InterpreterExample.java

src/test/java/
  LexerTest.java
  InterpreterTest.java
```

## Current Language Features

Expressions:

- Numbers: `2`, `3.5`
- Variables: `x`, `total_1`
- Unary operators: `+`, `-`
- Binary operators: `+`, `-`, `*`, `/`, `**`

Statements:

- Assignment: `x = expression`

## Lexer Usage

The lexer scans source text into tokens:

```java
lexer lexer = new lexer("x = 2 + 3 ** 2");
List<token> tokens = lexer.scanTokens();
```

Example output:

```text
IDENTIFIER "x"
ASSIGN "="
NUMBER "2"
PLUS "+"
NUMBER "3"
POWER "**"
NUMBER "2"
EOF ""
```

Run the lexer example:

```bash
mvn test
$JAVA_HOME/bin/java -cp target/classes examples.LexerExample
```

## Interpreter Usage

The interpreter currently runs AST nodes directly.

Evaluate an expression:

```java
interpreter interpreter = new interpreter();

double result = interpreter.eval(
        new binary(new number(2), "+", new binary(new number(3), "*", new number(4)))
);
```

Execute a statement:

```java
environment env = interpreter.exec(new assign("x", new number(2)));
double x = env.lookup("x");
```

Execute a program:

```java
environment env = interpreter.execProgram(List.of(
        new assign("x", new number(2)),
        new assign("y", new binary(new variable("x"), "**", new number(3)))
));
```

Run the interpreter example:

```bash
mvn test
$JAVA_HOME/bin/java -cp target/classes examples.InterpreterExample
```

## Testing

Run all tests:

```bash
mvn test
```

The current test suite covers:

- Expression evaluation
- Unary and binary operators
- Assignment execution
- Program execution
- Lexer tokenization
- Invalid lexer characters

## Next Steps

The next major feature is a parser:

1. Take `List<token>` from the lexer.
2. Parse expressions with precedence rules.
3. Parse assignment statements.
4. Return AST nodes that `interpreter` can execute.

Once the parser exists, source text like this:

```text
x = 2 + 3
y = x ** 2
```

can flow through:

```text
source text -> lexer -> parser -> AST -> interpreter
```
