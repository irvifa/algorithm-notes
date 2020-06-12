package com.github.irvifa.algorithmnotes.courses.google.weekthree;

import java.util.Scanner;

class Parser {

    Parser() {
    }

    public double stringToInteger(String input) {
        double multiplicand = 0;
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (character < '0' || character > '9') {
                throw new NumberFormatException("Can't convert character to digit: " + character);
            }
            int digit = character - '0';
            multiplicand += digit * Math.pow(10, input.length() - i - 1);
        }
        return multiplicand;
    }
}

public class EvaluateMathExpression {
    private static Parser parser;

    private static double evaluateOperator(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Can't divide by 0");
                }
                return num1 / num2;
            default:
                throw new NumberFormatException("Can't convert character to operator: +, -, /, *");
        }
    }

    public static double evaluateMathExpression(String expression) {
        String[] expressionArr = expression.split(" ");
        if (expressionArr.length != 3) {
            throw new IllegalArgumentException("Must have 3 tokens separated by spaces");
        }
        // s1 is first number.
        String value1Str = expressionArr[0];
        // s2 is math operator (e.g. '+').
        String operator = expressionArr[1];
        // s3 is second number.
        String value2Str = expressionArr[2];

        // first number
        double value1 = parser.stringToInteger(value1Str);

        // second number
        double value2 = parser.stringToInteger(value2Str);

        return evaluateOperator(value1, value2, operator);
    }

    private static String eval(String input) {
        return String.valueOf(evaluateMathExpression(input));
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        String s = cin.nextLine();
        cin.close();
        parser = new Parser();
        try {
            System.out.println(eval(s));
        } catch (NumberFormatException numberFormatException) {
            System.out.println(
                    ExceptionHandler.getFormattedErrorMessage(
                            numberFormatException.getClass().getSimpleName(),
                            numberFormatException.getMessage()));
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println(ExceptionHandler.getFormattedErrorMessage(
                    illegalArgumentException.getClass().getSimpleName(),
                    illegalArgumentException.getMessage()));
        }
    }

    static class ExceptionHandler {
        private ExceptionHandler() {
        }

        public static String getFormattedErrorMessage(String className, String message) {
            return className + " " + message;
        }
    }
}
