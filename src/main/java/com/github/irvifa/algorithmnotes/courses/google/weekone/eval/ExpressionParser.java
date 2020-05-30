//package com.github.irvifa.algorithmnotes.courses.google.weekone.eval;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Scanner;
//
//
//public class ExpressionParser<T> {
//    private final HashMap<String, Symbol> prefix;
//    private final HashMap<String, Symbol> infix;
//    private final HashSet<String> otherSymbols;
//    private final HashSet<String> primary;
//    private final HashMap<String, String[]> calls;
//    private final HashMap<String, String[]> groups;
//    private final Parser<T> parser;
//    private final int strongImplicitOperatorPrecedence;
//    private final int weakImplicitOperatorPrecedence;
//
//    public ExpressionParser(Parser<T> parser) {
//        this.parser = parser;
//        groups = new HashMap<>();
//        calls = new HashMap<>();
//        primary = new HashSet<>();
//        otherSymbols = new HashSet<>();
//        infix = new HashMap<>();
//        prefix = new HashMap<>();
//        strongImplicitOperatorPrecedence = -1;
//        weakImplicitOperatorPrecedence = -1;
//    }
//
//    public void addOperators(OperatorType type, int precedence, String... names) {
//        for (String name : names) {
//            infix.put(name, new Symbol(precedence, type));
//        }
//    }
//
//    public Iterable<String> getSymbols() {
//        HashSet<String> result = new HashSet<>();
//        result.addAll(otherSymbols);
//        result.addAll(infix.keySet());
//        result.addAll(prefix.keySet());
//        result.addAll(primary);
//        return result;
//    }
//
//    public T parse(String expr) {
//        Tokenizer tokenizer = new Tokenizer(new Scanner(expr), getSymbols());
//        tokenizer.nextToken();
//        T result = parse(tokenizer);
//        if (tokenizer.currentType != Tokenizer.TokenType.EOF) {
//            throw tokenizer.exceptionBuilder("Leftover input.", null);
//        }
//        return result;
//    }
//
//    public T parse(Tokenizer tokenizer) {
//        try {
//            return parseOperator(tokenizer, -1);
//        } catch (ParsingException e) {
//            throw e;
//        } catch (Exception e) {
//            throw tokenizer.exceptionBuilder(e.getMessage(), e);
//        }
//    }
//
//    private T parsePrefix(Tokenizer tokenizer) {
//        String token = tokenizer.currentValue;
//        Symbol prefixSymbol = prefix.get(token);
//        if (prefixSymbol == null) {
//            return parsePrimary(tokenizer);
//        }
//        tokenizer.nextToken();
//        T operand = parseOperator(tokenizer, prefixSymbol.precedence);
//        return parser.prefixOperator(tokenizer, token, operand);
//    }
//
//
//    private T parseOperator(Tokenizer tokenizer, int precedence) {
//        T left = parsePrefix(tokenizer);
//
//        while (true) {
//            String token = tokenizer.currentValue;
//            Symbol symbol = infix.get(token);
//            if (symbol == null) {
//                if (token.equals("") || otherSymbols.contains(token)) {
//                    break;
//                }
//                boolean strong = tokenizer.leadingWhitespace.isEmpty();
//                int implicitPrecedence = strong ? strongImplicitOperatorPrecedence : weakImplicitOperatorPrecedence;
//                if (!(implicitPrecedence > precedence)) {
//                    break;
//                }
//                T right = parseOperator(tokenizer, implicitPrecedence);
//                left = parser.implicitOperator(tokenizer, strong, left, right);
//            } else {
//                if (!(symbol.precedence > precedence)) {
//                    break;
//                }
//                tokenizer.nextToken();
//                if (symbol.type == null) {
//                    if (symbol.close == null) {
//                        T middle = parseOperator(tokenizer, -1);
//                        tokenizer.next(symbol.separator);
//                        T right = parseOperator(tokenizer, symbol.precedence);
//                        left = parser.ternaryOperator(tokenizer, token, left, middle, right);
//                    } else {
//                        List<T> list = parseList(tokenizer, symbol.separator, symbol.close);
//                        left = parser.apply(tokenizer, left, token, list);
//                    }
//                } else {
//                    switch (symbol.type) {
//                        case INFIX: {
//                            T right = parseOperator(tokenizer, symbol.precedence);
//                            left = parser.infixOperator(tokenizer, token, left, right);
//                            break;
//                        }
//                        case INFIX_RTL: {
//                            T right = parseOperator(tokenizer, symbol.precedence - 1);
//                            left = parser.infixOperator(tokenizer, token, left, right);
//                            break;
//                        }
//                        default:
//                            throw new IllegalStateException();
//                    }
//                }
//            }
//        }
//        return left;
//    }
//
//    List<T> parseList(Tokenizer tokenizer, String separator, String close) {
//        ArrayList<T> elements = new ArrayList<>();
//        if (!tokenizer.currentValue.equals(close)) {
//            while (true) {
//                elements.add(parse(tokenizer));
//                String token = tokenizer.currentValue;
//                if (token.equals(close)) {
//                    break;
//                }
//                if (separator == null) {
//                    throw tokenizer.exceptionBuilder("Closing bracket expected: '" + close + "'.", null);
//                }
//                if (!separator.isEmpty()) {
//                    if (!token.equals(separator)) {
//                        throw tokenizer.exceptionBuilder("List separator '" + separator + "' or closing paren '"
//                                + close + " expected.", null);
//                    }
//                    tokenizer.nextToken();
//                }
//            }
//        }
//        tokenizer.nextToken();
//        return elements;
//    }
//
//    T parsePrimary(Tokenizer tokenizer) {
//        String token = tokenizer.currentValue;
//        if (groups.containsKey(token)) {
//            tokenizer.nextToken();
//            String[] grouping = groups.get(token);
//            return parser.group(tokenizer, token, parseList(tokenizer, grouping[0], grouping[1]));
//        }
//
//        if (primary.contains(token)) {
//            tokenizer.nextToken();
//            return parser.primary(tokenizer, token);
//        }
//
//        T result;
//        switch (tokenizer.currentType) {
//            case NUMBER:
//                tokenizer.nextToken();
//                result = parser.numberLiteral(tokenizer, token);
//                break;
//            case IDENTIFIER:
//                tokenizer.nextToken();
//                if (calls.containsKey(tokenizer.currentValue)) {
//                    String openingBracket = tokenizer.currentValue;
//                    String[] call = calls.get(openingBracket);
//                    tokenizer.nextToken();
//                    result = parser.call(tokenizer, token, openingBracket, parseList(tokenizer, call[0], call[1]));
//                } else {
//                    result = parser.identifier(tokenizer, token);
//                }
//                break;
//            case STRING:
//                tokenizer.nextToken();
//                result = parser.stringLiteral(tokenizer, token);
//                break;
//            default:
//                throw tokenizer.exceptionBuilder("Unexpected token type.", null);
//        }
//        return result;
//    }
//}
