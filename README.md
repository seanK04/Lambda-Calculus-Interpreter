# Lambda Calculus Interpreter

A simple lambda calculus interpreter written in Java, featuring core functionalities such as variable binding, function application, alpha conversion, and beta reduction.

## Inspiration

This project was inspired by my experience in taking a functional programming-centric introductory course (CSCI 0170) at Brown University in Fall 2023. During the course, I built a Racket interpreter using ReasonML, which sparked my interest in interpreters and functional programming. My fascination deepened after watching a ComputerPhile video on Lambda Calculus, where I discovered the elegance and theoretical significance of this mathematical framework. These experiences motivated me to create this lambda calculus interpreter.

## Features

- **Variable Binding**: Handle variable references within expressions.
- **Function Application**: Apply functions to arguments.
- **Alpha Conversion**: Ensure unique variable names to avoid clashes.
- **Beta Reduction**: Evaluate expressions by applying functions to arguments and simplifying them.

## Usage

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/yourusername/LambdaCalculusInterpreter.git
    cd LambdaCalculusInterpreter
    ```

2. **Compile the Program**:
    ```sh
    javac LambdaCalculusInterpreter.java
    ```

3. **Run the Program**:
    ```sh
    java LambdaCalculusInterpreter
    ```

4. **Enter Lambda Calculus Expressions**:
    - Use `\` for lambda abstractions, e.g., `\x.x`
    - Use parentheses for applications, e.g., `(\x.x y)`

### Example

```sh
$ java LambdaCalculusInterpreter
Enter a lambda calculus expression:
(\x.x y)
Result: y
Enter another lambda calculus expression or type 'exit' to quit:
((\x.\y.x) a) b
Result: a
Enter another lambda calculus expression or type 'exit' to quit:
exit
