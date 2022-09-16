package org.jerry.calculator;

import java.util.List;

public class Calculator {

    public String calculate(String expression) {
        Scanner scanner = new Scanner(expression);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Expr expr = parser.parse();
        return String.valueOf(expr.accept(new ExprVisitor()));
    }

}
