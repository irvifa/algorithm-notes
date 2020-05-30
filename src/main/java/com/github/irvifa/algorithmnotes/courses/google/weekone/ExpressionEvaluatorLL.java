package com.github.irvifa.algorithmnotes.courses.google.weekone;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class ExpressionEvaluatorLL {
    private final Set<Character> operators;
    private final Set<Character> validCharacters;

    public ExpressionEvaluatorLL(String expr) {
        expr_ = expr;
        position_ = -1;
        isSuccess = true;
        operators = new HashSet<>();
        validCharacters = new HashSet<>();
        operators.addAll(Arrays.asList('+', '*', '/'));
        validCharacters.addAll(Arrays.asList('a', 'b', 's', 'q', 'r', 't'));
        validCharacters.addAll(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'));
        validCharacters.addAll(Arrays.asList('+', '*', '/', '-', '^', '.', ' ', '(', ')'));
        isValid = true;
    }

    void next() {
        curr_char_ = ++position_ < expr_.length() ? expr_.charAt(position_) : -1;
    }

    boolean should_apply(char c) {
        if (curr_char_ == c && validCharacters.contains((char) curr_char_) && curr_char_ <= 127) {
            next();
            if (position_ < expr_.length() && operators.contains(expr_.charAt(position_ - 1)) && operators.contains(expr_.charAt(position_))) {
                if (position_ + 1 < expr_.length() && expr_.charAt(position_ + 1) != '(') {
                    isValid = false;
                }
            }
            return true;
        } else if (!validCharacters.contains((char)curr_char_) && curr_char_ >= 0 && curr_char_ <=127) {
            isValid = false;
        }
        return false;
    }

    String eval() {
        expr_ = expr_.replaceAll("\\s+","");
        next();
        String result = String.format("%.2f", evalExpr());
        if (isSuccess && isValid) {
            return result;
        } else {
            return "Invalid mathematical expression.";
        }
    }

    boolean is_abs_operator() {
        return should_apply('a') && should_apply('b') && should_apply('s');
    }

    boolean is_sqrt_operator() {
        return should_apply('s') && should_apply('q')
                && should_apply('r') && should_apply('t');
    }

    double eval_power() {
        double result = eval_factor();
        while(true) {
            if (should_apply('^')) {
                if (result < 0) {
                    isSuccess = false;
                    continue;
                }
                result = Math.pow(result, eval_factor());
            }
            else return result;
        }
    }

    double eval_abs_sqrt() {
        double result = eval_power();
        while(true) {
            if (is_abs_operator()) {
                result = Math.abs(eval_power());
            } else if (is_sqrt_operator()) {
                double res = eval_power();
                if (res < 0) {
                    isSuccess = false;
                    continue;
                }
                result = Math.sqrt(res);
            }
            else return result;
        }
    }

    double eval_term() {
        double result = eval_abs_sqrt();
        while(true) {
            if (should_apply('*')) {
                result *= eval_abs_sqrt();
            } else if (should_apply('/')) {
                double res = eval_abs_sqrt();
                if (res == 0) {
                    isSuccess = false;
                    continue;
                }
                result /= res;
            }
            else return result;
        }
    }

    double evalExpr() {
        double result = eval_term();
        while (true) {
            if (should_apply('+')) {
                result += eval_term();
            } else if (should_apply('-')) {
                result -= eval_term();
            }
            else return result;
        }
    }

    double eval_factor() {
        double result = 0.0;
        if (should_apply('-')) {
            result = - evalExpr();
        } else if (should_apply('(')) {
            result = evalExpr();
            should_apply(')');
        } else if ((curr_char_ >= '0' && curr_char_ <= '9') || curr_char_ == '.') {
            StringBuilder sb = new StringBuilder();
            while (curr_char_ >= '0' && curr_char_ <= '9') {
                sb.append((char)curr_char_);
                next();
            }
            if (curr_char_ == '.') {
                sb.append((char)curr_char_);
                next();
                while (curr_char_ >= '0' && curr_char_ <= '9') {
                    sb.append((char)curr_char_);
                    next();
                }
            }
            result = Double.valueOf(sb.toString());
        }
        return result;
    }
    int position_;
    int curr_char_;
    String expr_;
    boolean isSuccess;
    boolean isValid;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        System.out.println(new ExpressionEvaluatorLL(expression).eval());
        scanner.close();
    }
}
