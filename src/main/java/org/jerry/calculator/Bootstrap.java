package org.jerry.calculator;

public class Bootstrap {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.calculate("2*(3+4)-1.45"));
    }

}
