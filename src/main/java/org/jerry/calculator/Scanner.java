package org.jerry.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描器，将算数表达式解析为Token序列
 */
public class Scanner {
    private final char[] chs;
    private final List<Token> tokens = new ArrayList<>();
    private int current;

    public Scanner(String source) {
        this.chs = source.toCharArray();
        this.current = 0;
    }

    public List<Token> scanTokens() {
        while (!isEnd()) {
            switch (peek()) {
                case '+':
                    consume();
                    tokens.add(new Token(TokenType.PLUS));
                    break;
                case '-':
                    consume();
                    tokens.add(new Token(TokenType.MINUS));
                    break;
                case '*':
                    consume();
                    tokens.add(new Token(TokenType.MULTI));
                    break;
                case '/':
                    consume();
                    tokens.add(new Token(TokenType.DIVIDE));
                    break;
                case '(':
                    consume();
                    tokens.add(new Token(TokenType.LEFT_PARENTHESIS));
                    break;
                case ')':
                    consume();
                    tokens.add(new Token(TokenType.RIGHT_PARENTHESIS));
                    break;
                default:
                    if (isNumeric(peek())) {
                        tokens.add(new Token(TokenType.NUMBER, consumeNumber()));
                    } else {
                        throw new IllegalStateException("unexpected character: " + peek());
                    }
            }
        }
        return tokens;
    }

    private boolean isNumeric(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private char peek() {
        return chs[current];
    }

    private char consume() {
        return chs[current++];
    }

    private double consumeNumber() {
        int start = current;
        skipNumbers();
        if (peek() == '.') {
            consume();
            skipNumbers();
        }
        return Double.parseDouble(new String(chs, start, current - start));
    }

    private void skipNumbers() {
        while(!isEnd() && isNumeric(peek())) {
            current++;
        }
    }

    private boolean isEnd() {
        return current == chs.length;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner("2*(3+4)-1.45");
        System.out.println(scanner.scanTokens());
    }

}
