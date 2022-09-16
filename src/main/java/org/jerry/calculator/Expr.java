package org.jerry.calculator;

public abstract class Expr {

    public abstract double accept(ExprVisitor visitor);

    public static class Number extends Expr {
        private final double value;

        public Number(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public double accept(ExprVisitor visitor) {
            return visitor.visitNumber(this);
        }
    }

    public static class BinaryOp extends Expr {
        private final Expr left;
        private final TokenType operator;
        private final Expr right;

        public BinaryOp(Expr left, TokenType operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public Expr getLeft() {
            return left;
        }

        public TokenType getOperator() {
            return operator;
        }

        public Expr getRight() {
            return right;
        }

        @Override
        public double accept(ExprVisitor visitor) {
            return visitor.visitBinary(this);
        }
    }

    public static class UnaryOp extends Expr {
        private final TokenType operator;
        private final Expr operand;

        public UnaryOp(TokenType operator, Expr operand) {
            this.operator = operator;
            this.operand = operand;
        }

        public TokenType getOperator() {
            return operator;
        }

        public Expr getOperand() {
            return operand;
        }

        @Override
        public double accept(ExprVisitor visitor) {
            return visitor.visitUnary(this);
        }
    }

    public static class Group extends Expr {
        private final Expr expr;

        public Group(Expr expr) {
            this.expr = expr;
        }

        public Expr getExpr() {
            return expr;
        }

        @Override
        public double accept(ExprVisitor visitor) {
            return visitor.visitGroup(this);
        }
    }



}
