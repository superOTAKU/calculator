package org.jerry.calculator;

public class ExprVisitor {

    public double visitNumber(Expr.Number number) {
        return number.getValue();
    }

    public double visitUnary(Expr.UnaryOp unary) {
        return -1.0 * unary.getOperand().accept(this);
    }

    public double visitBinary(Expr.BinaryOp unary) {
        double left = unary.getLeft().accept(this);
        double right = unary.getRight().accept(this);
        switch (unary.getOperator()) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case MULTI:
                return left * right;
            case DIVIDE:
                return left / right;
        }
        throw new IllegalStateException("never happen!");
    }

    public double visitGroup(Expr.Group group) {
        return group.getExpr().accept(this);
    }





}
