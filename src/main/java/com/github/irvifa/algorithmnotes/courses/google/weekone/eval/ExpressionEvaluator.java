package com.github.irvifa.algorithmnotes.courses.google.weekone.eval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Pattern;

enum OperatorType {
    INFIX, INFIX_RTL, PREFIX
}

enum TokenType {
    UNRECOGNIZED, BOF, IDENTIFIER, SYMBOL, NUMBER, STRING, EOF
}

class ExpressionEvaluator {
    final String expr_;
    final ExpressionParser<Double> parser;

    public ExpressionEvaluator(String expr) {
        expr_ = expr;
        parser = DoubleParser.createParser();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        System.out.println(new ExpressionEvaluator(expression).eval());
        scanner.close();
    }

    public String eval() {
        try {
            Double res = parser.parse(expr_);
            String resultStr = String.format("%.2f", res);
            if (!resultStr.equalsIgnoreCase("NaN") && !resultStr.equalsIgnoreCase("Infinity")) {
                return resultStr;
            }
            return "Invalid mathematical expression.";
        } catch (Exception e) {
            return "Invalid mathematical expression.";
        }
    }
}

class DoubleParser extends Parser<Double> {
    static ExpressionParser<Double> createParser() {
        ExpressionParser<Double> parser = new ExpressionParser<>(new DoubleParser());
        parser.addCallBrackets("(", ",", ")");
        parser.addGroupBrackets("(", null, ")");
        parser.addOperators(OperatorType.INFIX_RTL, 4, "^");
        parser.addOperators(OperatorType.PREFIX, 3, "-");
        parser.setImplicitOperatorPrecedence(true, 2);
        parser.setImplicitOperatorPrecedence(false, 2);
        parser.addOperators(OperatorType.INFIX, 1, "*", "/");
        parser.addOperators(OperatorType.INFIX, 0, "+", "-");
        return parser;
    }

    @Override
    public Double infixOperator(Tokenizer tokenizer, String name, Double left, Double right) {
        switch (name.charAt(0)) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                return left / right;
            case '^': {
                if (left < 0) {
                    throw new ParsingException("Invalid expression", null);
                }
                return Math.pow(left, right);
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Double implicitOperator(Tokenizer tokenizer, boolean strong, Double left, Double right) {
        return left * right;
    }

    @Override
    public Double prefixOperator(Tokenizer tokenizer, String name, Double argument) {
        return name.equals("-") ? -argument : argument;
    }

    @Override
    public Double numberLiteral(Tokenizer tokenizer, String value) {
        return Double.parseDouble(value);
    }

    @Override
    public Double identifier(Tokenizer tokenizer, String name) {
        return 0.0;
    }

    @Override
    public Double group(Tokenizer tokenizer, String paren, List<Double> elements) {
        return elements.get(0);
    }

    @Override
    public Double call(Tokenizer tokenizer, String identifier, String bracket, List<Double> arguments) {
        if (arguments.size() == 1) {
            try {
                return (Double) Math.class.getMethod(
                        identifier, new Class[]{Double.TYPE}).invoke(null, arguments.get(0));
            } catch (Exception e) {
            }
        }
        return super.call(tokenizer, identifier, bracket, arguments);
    }
}

class ParsingException extends RuntimeException {
    public ParsingException(String text, Exception base) {
        super(text, base);
    }
}

class Symbol {
    final int precedence;
    final OperatorType type;
    final String separator;
    final String close;

    Symbol(int precedence, OperatorType type) {
        this.precedence = precedence;
        this.type = type;
        this.separator = null;
        this.close = null;
    }
}

class Tokenizer {
    public static final Pattern DEFAULT_NUMBER_PATTERN = Pattern.compile(
            "\\G\\s*(\\d+(\\.\\d*)?|\\.\\d+)([eE][+-]?\\d+)?");

    public static final Pattern DEFAULT_IDENTIFIER_PATTERN = Pattern.compile(
            "\\G\\s*[\\p{Alpha}_$][\\p{Alpha}_$\\d]*");

    public static final Pattern DEFAULT_STRING_PATTERN = Pattern.compile(
            "\\G\\s*(\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(\\\\.[^'\\\\]*)*)')");
    public static final Pattern DEFAULT_END_PATTERN = Pattern.compile("\\G\\s*\\Z");

    public static final Pattern DEFAULT_LINE_COMMENT_PATTERN = Pattern.compile("\\G\\h*#.*(\\v|\\Z)");

    public static final Pattern DEFAULT_NEWLINE_PATTERN = Pattern.compile("\\G\\h*\\v");
    protected final Scanner scanner;
    public Pattern numberPattern = DEFAULT_NUMBER_PATTERN;
    public Pattern identifierPattern = DEFAULT_IDENTIFIER_PATTERN;
    public Pattern stringPattern = DEFAULT_STRING_PATTERN;
    public Pattern endPattern = DEFAULT_END_PATTERN;
    public Pattern newlinePattern = DEFAULT_NEWLINE_PATTERN;
    public Pattern lineCommentPattern = DEFAULT_LINE_COMMENT_PATTERN;
    public Pattern symbolPattern;
    public int currentLine = 1;
    public int lastLineStart = 0;
    public int currentPosition = 0;
    public String currentValue = "";
    public TokenType currentType = TokenType.BOF;
    public String leadingWhitespace = "";
    public boolean insertSemicolons;
    private final StringBuilder skippedComments = new StringBuilder();

    public Tokenizer(Scanner scanner, Iterable<String> symbols, String... additionalSymbols) {
        this.scanner = scanner;
        StringBuilder sb = new StringBuilder("\\G\\s*(");

        TreeSet<String> sorted = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int dl = -Integer.compare(s1.length(), s2.length());
                return dl == 0 ? s1.compareTo(s2) : dl;
            }
        });
        for (String symbol : symbols) {
            sorted.add(symbol);
        }
        Collections.addAll(sorted, additionalSymbols);
        for (String s : sorted) {
            sb.append(Pattern.quote(s));
            sb.append('|');
        }
        sb.setCharAt(sb.length() - 1, ')');
        symbolPattern = Pattern.compile(sb.toString());
    }

    public TokenType next(String expected, String errorMessage) {
        if (!hasNext(expected)) {
            throw exception(errorMessage, null);
        }
        return currentType;
    }

    public TokenType next(String expected) {
        return next(expected, "Expected: '" + expected + "'.");
    }

    public ParsingException exception(String message, Exception cause) {
        return new ParsingException(
                message, cause);
    }

    protected boolean insertSemicolon() {
        return (currentType == TokenType.IDENTIFIER || currentType == TokenType.NUMBER ||
                currentType == TokenType.STRING ||
                (currentValue.length() == 1 && ")]}".indexOf(currentValue) != -1));
    }

    public TokenType nextToken() {
        currentPosition += currentValue.length();
        String value;
        if (scanner.ioException() != null) {
            throw exception("IO Exception: " + scanner.ioException().getMessage(), scanner.ioException());
        }

        boolean newLine = false;
        while (true) {
            if ((value = scanner.findWithinHorizon(lineCommentPattern, 0)) != null) {
                skippedComments.append(value.trim()).append("\n");
            } else if ((value = scanner.findWithinHorizon(newlinePattern, 0)) == null) {
                break;
            }
            newLine = true;
            currentPosition += value.length();
            currentLine++;
            lastLineStart = currentPosition;
        }

        if (newLine && insertSemicolons && insertSemicolon()) {
            value = ";";
            currentPosition--;
            currentType = TokenType.SYMBOL;
        } else if ((value = scanner.findWithinHorizon(identifierPattern, 0)) != null) {
            currentType = TokenType.IDENTIFIER;
        } else if ((value = scanner.findWithinHorizon(numberPattern, 0)) != null) {
            currentType = TokenType.NUMBER;
        } else if ((value = scanner.findWithinHorizon(stringPattern, 0)) != null) {
            currentType = TokenType.STRING;
        } else if ((value = scanner.findWithinHorizon(symbolPattern, 0)) != null) {
            currentType = TokenType.SYMBOL;
        } else if ((value = scanner.findWithinHorizon(endPattern, 0)) != null) {
            currentType = TokenType.EOF;
        } else if ((value = scanner.findWithinHorizon("\\G\\s*\\S*", 0)) != null) {
            currentType = TokenType.UNRECOGNIZED;
        } else {
            currentType = TokenType.UNRECOGNIZED;
            throw exception("EOF not reached, but catchall not matched.", null);
        }
        if (value.length() > 0 && value.charAt(0) <= ' ') {
            currentValue = value.trim();
            leadingWhitespace = value.substring(0, value.length() - currentValue.length());
            int pos = 0;
            while (true) {
                int j = leadingWhitespace.indexOf('\n', pos);
                if (j == -1) {
                    break;
                }
                pos = j + 1;
                currentLine++;
                lastLineStart = currentPosition + j;
            }
            currentPosition += leadingWhitespace.length();
        } else {
            leadingWhitespace = "";
            currentValue = value;
        }
        return currentType;
    }

    public boolean hasNext(String value) {
        if (!currentValue.equals(value)) {
            return false;
        }
        nextToken();
        return true;
    }
}

class Parser<T> {

    public T apply(Tokenizer tokenizer, T base, String bracket, List<T> arguments) {
        throw new UnsupportedOperationException(
                "apply(" + base + ", " + bracket + ", " + arguments + ")");
    }

    public T call(Tokenizer tokenizer, String identifier, String bracket, List<T> arguments) {
        throw new UnsupportedOperationException(
                "call(" + identifier + ", " + bracket + ", " + arguments + ")");
    }

    public T group(Tokenizer tokenizer, String paren, List<T> elements) {
        throw new UnsupportedOperationException("group(" + paren + ", " + elements + ')');
    }

    public T identifier(Tokenizer tokenizer, String name) {
        throw new UnsupportedOperationException("identifier(" + name + ')');
    }

    public T implicitOperator(Tokenizer tokenizer, boolean strong, T left, T right) {
        throw new UnsupportedOperationException("implicitOperator(" + left + ", " + right + ')');
    }

    public T infixOperator(Tokenizer tokenizer, String name, T left, T right) {
        throw new UnsupportedOperationException("infixOperator(" + name + ", " + left + ", " + right + ')');
    }

    public T numberLiteral(Tokenizer tokenizer, String value) {
        throw new UnsupportedOperationException("numberLiteral(" + value + ")");
    }

    public T prefixOperator(Tokenizer tokenizer, String name, T argument) {
        throw new UnsupportedOperationException("prefixOperator(" + name + ", " + argument + ')');
    }

    public T primary(Tokenizer tokenizer, String name) {
        throw new UnsupportedOperationException("primary(" + name + ", " + tokenizer + ")");
    }

    public T stringLiteral(Tokenizer tokenizer, String value) {
        throw new UnsupportedOperationException("stringLiteral(" + value + ')');
    }

    public T ternaryOperator(Tokenizer tokenizer, String operator, T left, T middle, T right) {
        throw new UnsupportedOperationException("ternaryOperator(" + operator + ')');
    }
}

class ExpressionParser<T> {

    private final HashMap<String, Symbol> prefix;
    private final HashMap<String, Symbol> infix;
    private final HashSet<String> otherSymbols;
    private final HashSet<String> primary;
    private final HashMap<String, String[]> calls;
    private final HashMap<String, String[]> groups;

    private final Parser<T> parser;
    private int strongImplicitOperatorPrecedence;
    private int weakImplicitOperatorPrecedence;

    public ExpressionParser(Parser<T> parser) {
        this.parser = parser;
        this.prefix = new HashMap<>();
        infix = new HashMap<>();
        otherSymbols = new HashSet<>();
        primary = new HashSet<>();
        calls = new HashMap<>();
        groups = new HashMap<>();
        strongImplicitOperatorPrecedence = -1;
        weakImplicitOperatorPrecedence = -1;
    }

    public void addCallBrackets(String open, String separator, String close) {
        calls.put(open, new String[]{separator, close});
        otherSymbols.add(open);
        if (separator != null) {
            otherSymbols.add(separator);
        }
        otherSymbols.add(close);
    }

    public void addGroupBrackets(String open, String separator, String close) {
        groups.put(open, new String[]{separator, close});
        otherSymbols.add(open);
        if (separator != null) {
            otherSymbols.add(separator);
        }
        otherSymbols.add(close);
    }

    public void addOperators(OperatorType type, int precedence, String... names) {
        for (String name : names) {
            if (type == OperatorType.PREFIX) {
                prefix.put(name, new Symbol(precedence, type));
            } else {
                infix.put(name, new Symbol(precedence, type));
            }
        }
    }

    public void setImplicitOperatorPrecedence(boolean strong, int precedence) {
        if (strong) {
            strongImplicitOperatorPrecedence = precedence;
        } else {
            weakImplicitOperatorPrecedence = precedence;
        }
    }

    private Iterable<String> getSymbols() {
        HashSet<String> result = new HashSet<>();
        result.addAll(otherSymbols);
        result.addAll(infix.keySet());
        result.addAll(prefix.keySet());
        result.addAll(primary);
        return result;
    }


    public T parse(String expr) {
        Tokenizer tokenizer = new Tokenizer(new Scanner(expr), getSymbols());
        tokenizer.nextToken();
        T result = parse(tokenizer);
        if (tokenizer.currentType != TokenType.EOF) {
            throw tokenizer.exception("Leftover input.", null);
        }
        return result;
    }

    public T parse(Tokenizer tokenizer) {
        try {
            return parseOperator(tokenizer, -1);
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw tokenizer.exception(e.getMessage(), e);
        }
    }

    private T parsePrefix(Tokenizer tokenizer) {
        String token = tokenizer.currentValue;
        Symbol prefixSymbol = prefix.get(token);
        if (prefixSymbol == null) {
            return parsePrimary(tokenizer);
        }
        tokenizer.nextToken();
        T operand = parseOperator(tokenizer, prefixSymbol.precedence);
        return parser.prefixOperator(tokenizer, token, operand);
    }


    private T parseOperator(Tokenizer tokenizer, int precedence) {
        T left = parsePrefix(tokenizer);

        while (true) {
            String token = tokenizer.currentValue;
            Symbol symbol = infix.get(token);
            if (symbol == null) {
                if (token.equals("") || otherSymbols.contains(token)) {
                    break;
                }
                // Implicit operator
                boolean strong = tokenizer.leadingWhitespace.isEmpty();
                int implicitPrecedence = strong ? strongImplicitOperatorPrecedence : weakImplicitOperatorPrecedence;
                if (!(implicitPrecedence > precedence)) {
                    break;
                }
                T right = parseOperator(tokenizer, implicitPrecedence);
                left = parser.implicitOperator(tokenizer, strong, left, right);
            } else {
                if (!(symbol.precedence > precedence)) {
                    break;
                }
                tokenizer.nextToken();
                if (symbol.type == null) {
                    if (symbol.close == null) {
                        // Ternary
                        T middle = parseOperator(tokenizer, -1);
                        tokenizer.next(symbol.separator);
                        T right = parseOperator(tokenizer, symbol.precedence);
                        left = parser.ternaryOperator(tokenizer, token, left, middle, right);
                    } else {
                        // Group
                        List<T> list = parseList(tokenizer, symbol.separator, symbol.close);
                        left = parser.apply(tokenizer, left, token, list);
                    }
                } else {
                    switch (symbol.type) {
                        case INFIX: {
                            T right = parseOperator(tokenizer, symbol.precedence);
                            left = parser.infixOperator(tokenizer, token, left, right);
                            break;
                        }
                        case INFIX_RTL: {
                            T right = parseOperator(tokenizer, symbol.precedence - 1);
                            left = parser.infixOperator(tokenizer, token, left, right);
                            break;
                        }
                        default:
                            throw new IllegalStateException();
                    }
                }
            }
        }
        return left;
    }

    List<T> parseList(Tokenizer tokenizer, String separator, String close) {
        ArrayList<T> elements = new ArrayList<>();
        if (!tokenizer.currentValue.equals(close)) {
            while (true) {
                elements.add(parse(tokenizer));
                String operator = tokenizer.currentValue;
                if (operator.equals(close)) {
                    break;
                }
                if (separator == null) {
                    throw tokenizer.exception("Closing bracket expected: '" + close + "'.", null);
                }
                if (!separator.isEmpty()) {
                    if (!operator.equals(separator)) {
                        throw tokenizer.exception("List separator '" + separator + "' or closing paren '"
                                + close + " expected.", null);
                    }
                    tokenizer.nextToken();
                }
            }
        }
        tokenizer.nextToken();
        return elements;
    }

    T parsePrimary(Tokenizer tokenizer) {
        String token = tokenizer.currentValue;

        if (groups.containsKey(token)) {
            tokenizer.nextToken();
            String[] grouping = groups.get(token);
            return parser.group(tokenizer, token, parseList(tokenizer, grouping[0], grouping[1]));
        }

        if (primary.contains(token)) {
            tokenizer.nextToken();
            return parser.primary(tokenizer, token);
        }

        T result;
        switch (tokenizer.currentType) {
            case NUMBER:
                tokenizer.nextToken();
                result = parser.numberLiteral(tokenizer, token);
                break;
            case IDENTIFIER:
                if (!token.equals("abs") && !token.equals("sqrt")) {
                    throw tokenizer.exception("Unexpected token type.", null);
                }
                tokenizer.nextToken();
                if (calls.containsKey(tokenizer.currentValue)) {
                    String openingBracket = tokenizer.currentValue;
                    String[] call = calls.get(openingBracket);
                    tokenizer.nextToken();
                    result = parser.call(tokenizer, token, openingBracket, parseList(tokenizer, call[0], call[1]));
                } else {
                    result = parser.identifier(tokenizer, token);
                }
                break;
            case STRING:
                tokenizer.nextToken();
                result = parser.stringLiteral(tokenizer, token);
                break;
            default:
                if (token.equals(".")) {
                    tokenizer.nextToken();
                    result = parser.numberLiteral(tokenizer, "0.00");
                    break;
                }
                if (tokenizer.currentType == TokenType.EOF) {
                    tokenizer.nextToken();
                    result = parser.numberLiteral(tokenizer, "0.00");
                    break;
                }
                throw tokenizer.exception("Unexpected token type.", null);
        }
        return result;
    }
}
