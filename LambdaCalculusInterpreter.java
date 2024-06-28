import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

// Abstract class representing a lambda expression
abstract class Expression {
    abstract Expression evaluate(Map<String, Expression> context);

    abstract Expression substitute(String variable, Expression value);
}

// Variable class representing a variable in lambda calculus
class Variable extends Expression {
    String name;

    Variable(String name) {
        this.name = name;
    }

    @Override
    Expression evaluate(Map<String, Expression> context) {
        if (context.containsKey(name)) {
            return context.get(name).evaluate(context);
        }
        return this;
    }

    @Override
    Expression substitute(String variable, Expression value) {
        if (name.equals(variable)) {
            return value;
        }
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}

// Abstraction class representing a lambda abstraction (function) in lambda
// calculus
class Abstraction extends Expression {
    String variable;
    Expression body;

    Abstraction(String variable, Expression body) {
        this.variable = variable;
        this.body = body;
    }

    @Override
    Expression evaluate(Map<String, Expression> context) {
        return this;
    }

    @Override
    Expression substitute(String variable, Expression value) {
        if (this.variable.equals(variable)) {
            return this;
        }
        return new Abstraction(this.variable, body.substitute(variable, value));
    }

    @Override
    public String toString() {
        return "\\" + variable + "." + body;
    }
}

// Application class representing a function application in lambda calculus
class Application extends Expression {
    Expression function;
    Expression argument;

    Application(Expression function, Expression argument) {
        this.function = function;
        this.argument = argument;
    }

    @Override
    Expression evaluate(Map<String, Expression> context) {
        // Evaluate the function and the argument first
        Expression evaluatedFunction = function.evaluate(context);
        Expression evaluatedArgument = argument.evaluate(context);

        // Beta reduction: Apply the function to the argument if the function is an
        // abstraction
        if (evaluatedFunction instanceof Abstraction) {
            Abstraction abstraction = (Abstraction) evaluatedFunction;
            // Substitute the bound variable in the function body with the argument
            return abstraction.body.substitute(abstraction.variable, evaluatedArgument).evaluate(context);
        }

        // If the function is not an abstraction, return an application of the evaluated
        // function and argument
        return new Application(evaluatedFunction, evaluatedArgument);
    }

    @Override
    Expression substitute(String variable, Expression value) {
        return new Application(function.substitute(variable, value), argument.substitute(variable, value));
    }

    @Override
    public String toString() {
        return "(" + function + " " + argument + ")";
    }
}

// Parser for lambda calculus expressions
class Parser {
    private int index;
    private String[] tokens;

    public Parser(String input) {
        this.tokens = input.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim().split("\\s+");
        this.index = 0;
    }

    public Expression parse() {
        Stack<Expression> stack = new Stack<>();

        while (index < tokens.length) {
            String token = tokens[index++];
            if (token.equals(")")) {
                Expression arg = stack.pop();
                Expression func = stack.pop();
                stack.pop(); // Remove '('
                stack.push(new Application(func, arg));
            } else if (token.equals("(")) {
                stack.push(new Variable("("));
            } else if (token.startsWith("\\")) {
                // Lambda abstraction parsing
                String variable = token.substring(1); // Remove '\\' from variable
                StringBuilder body = new StringBuilder();
                int depth = 0;
                while (index < tokens.length) {
                    String currentToken = tokens[index++];
                    if (currentToken.equals("("))
                        depth++;
                    if (currentToken.equals(")"))
                        depth--;
                    if (depth < 0)
                        break;
                    body.append(currentToken).append(" ");
                }
                stack.push(new Abstraction(variable, new Parser(body.toString().trim()).parse()));
            } else {
                stack.push(new Variable(token));
            }
        }

        return stack.pop();
    }
}

public class LambdaCalculusInterpreter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a lambda calculus expression:");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("exit"))
                break;

            Parser parser = new Parser(input);
            Expression expression = parser.parse();
            Expression result = expression.evaluate(new HashMap<>());
            System.out.println("Result: " + result);
            System.out.println("Enter another lambda calculus expression or type 'exit' to quit:");
        }

        scanner.close();
    }
}
