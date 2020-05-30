//package com.github.irvifa.algorithmnotes.courses.google.weekone.eval;
//
//import java.util.List;
//
//
//public class Parser<T> {
//
//    public T apply(Tokenizer tokenizer, T base, String bracket, List<T> arguments) {
//        throw new UnsupportedOperationException(
//                "apply(" + base+ ", " + bracket + ", " + arguments + ")");
//    }
//
//    public T call(Tokenizer tokenizer, String identifier, String bracket, List<T> arguments) {
//        throw new UnsupportedOperationException(
//                "call(" + identifier + ", " + bracket + ", " + arguments + ")");
//    }
//
//    public T group(Tokenizer tokenizer, String paren, List<T> elements) {
//        throw new UnsupportedOperationException("group(" + paren + ", " + elements + ')');
//    }
//
//    public T identifier(Tokenizer tokenizer, String name) {
//        throw new UnsupportedOperationException("identifier(" + name + ')');
//    }
//
//    public T implicitOperator(Tokenizer tokenizer, boolean strong, T left, T right) {
//        throw new UnsupportedOperationException("implicitOperator(" + left + ", " + right + ')');
//    }
//
//    public T infixOperator(Tokenizer tokenizer, String name, T left, T right) {
//        throw new UnsupportedOperationException("infixOperator(" + name + ", " + left + ", " + right + ')');
//    }
//
//    public T numberLiteral(Tokenizer tokenizer, String value) {
//        throw new UnsupportedOperationException("numberLiteral(" + value + ")");
//    }
//
//    public T prefixOperator(Tokenizer tokenizer, String name, T argument) {
//        throw new UnsupportedOperationException("prefixOperator(" + name + ", " + argument + ')');
//    }
//
//    public T primary(Tokenizer tokenizer, String name) {
//        throw new UnsupportedOperationException("primary(" + name + ", " + tokenizer + ")");
//    }
//
//    public T stringLiteral(Tokenizer tokenizer, String value) {
//        throw new UnsupportedOperationException("stringLiteral(" + value + ')');
//    }
//
//    public T ternaryOperator(Tokenizer tokenizer, String operator, T left, T middle, T right) {
//        throw new UnsupportedOperationException("ternaryOperator(" + operator + ')');
//    }
//}
