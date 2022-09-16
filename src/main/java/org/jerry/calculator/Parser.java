package org.jerry.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <pre>
 * 解析器，将Token序列解析为AST
 *
 * BNF:
 *  expression = factor ( ( "+" | "-" ) factor )*
 *  factor = unary ( ( "*" | "/" ) unary )*
 *  unary = "-" unary | primary
 *  primary = NUMBER | "(" expression ")"
 *
 *
 * </pre>
 */
public class Parser {
    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
    }

    public Expr parse() {
        return expression();
    }

    private Expr expression() {
        Expr expr = factor();
        if (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = consume();
            expr = new Expr.BinaryOp(expr, operator.getType(), factor());
        }
        return expr;
    }

    private Expr factor() {
        Expr expr = unary();
        while (match(TokenType.MULTI, TokenType.DIVIDE)) {
            Token operator = consume();
            expr = new Expr.BinaryOp(expr, operator.getType(), unary());
        }
        return expr;
    }

    private Expr unary() {
        if (match(TokenType.MINUS)) {
            return new Expr.UnaryOp(consume().getType(), primary());
        } else {
            return primary();
        }
    }

    private Expr primary() {
        if (match(TokenType.LEFT_PARENTHESIS)) {
            consume();
            Expr expr = expression();
            Token token = consume();
            if (token.getType() != TokenType.RIGHT_PARENTHESIS) {
                throw new IllegalStateException("expect )");
            }
            return new Expr.Group(expr);
        } else {
            return number();
        }
    }

    private Expr number() {
        return new Expr.Number((double)consume().getValue());
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token consume() {
        return tokens.get(current++);
    }

    private boolean isEnd() {
        return current == tokens.size();
    }

    private boolean match(TokenType ... types) {
        return !isEnd() && Stream.of(types).anyMatch(type -> type == peek().getType());
    }

}
