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

class ExpressionEvaluator {
    final String expr_;
    final ExpressionParser<Double> parser;

    public ExpressionEvaluator(String expr) {
        expr_ = expr;
        parser = DoubleParser.createParser();
    }

    public String eval() {
        try {
            Double res = parser.parse(expr_);
            return String.format("%.2f", res);
        } catch (Exception e) {
            return "Invalid mathematical expression.";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        System.out.println(new ExpressionEvaluator(expression).eval());
        scanner.close();
    }
}

class DoubleParser extends Parser<Double> {
    @Override
    public Double infixOperator(Tokenizer tokenizer, String name, Double left, Double right) {
        switch (name.charAt(0)) {
            case '+': return left + right;
            case '-': return left - right;
            case '*': return left * right;
            case '/': return left / right;
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

    static ExpressionParser<Double> createParser() {
        ExpressionParser<Double> parser = new ExpressionParser<>(new DoubleParser());
        parser.addCallBrackets("(", ",", ")");
        parser.addGroupBrackets("(", null, ")");
        parser.addOperators(OperatorType.INFIX_RTL, 4, "^");
        parser.addOperators(OperatorType.PREFIX, 3, "+", "-");
        parser.setImplicitOperatorPrecedence(true, 2);
        parser.setImplicitOperatorPrecedence(false, 2);
        parser.addOperators(OperatorType.INFIX, 1, "*", "/");
        parser.addOperators(OperatorType.INFIX, 0, "+", "-");
        return parser;
    }
}

enum OperatorType {
    INFIX, INFIX_RTL, PREFIX, SUFFIX
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
    Symbol(int precedence, String separator, String close) {
        this.precedence = precedence;
        this.type = null;
        this.separator = separator;
        this.close = close;
    }
}

class Tokenizer {
    public static final Pattern DEFAULT_NUMBER_PATTERN = Pattern.compile(
            "\\G\\s*(\\d+(\\.\\d*)?|\\.\\d+)([eE][+-]?\\d+)?");

    public static final Pattern DEFAULT_IDENTIFIER_PATTERN = Pattern.compile(
            "\\G\\s*[\\p{Alpha}_$][\\p{Alpha}_$\\d]*");

    public static final Pattern DEFAULT_STRING_PATTERN = Pattern.compile(
            // "([^"\\]*(\\.[^"\\]*)*)"|\'([^\'\\]*(\\.[^\'\\]*)*)\'
            "\\G\\s*(\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(\\\\.[^'\\\\]*)*)')");
    public static final Pattern DEFAULT_END_PATTERN = Pattern.compile("\\G\\s*\\Z");

    public static final Pattern DEFAULT_LINE_COMMENT_PATTERN = Pattern.compile("\\G\\h*#.*(\\v|\\Z)");

    public static final Pattern DEFAULT_NEWLINE_PATTERN = Pattern.compile("\\G\\h*\\v");

    public enum TokenType {
        UNRECOGNIZED, BOF, IDENTIFIER, SYMBOL, NUMBER, STRING, EOF
    }

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

    protected final Scanner scanner;
    private StringBuilder skippedComments = new StringBuilder();

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
        for (String symbol: symbols) {
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

    public int currentColumn() {
        return  currentPosition - lastLineStart + 1;
    }

    public TokenType consume(String expected, String errorMessage) {
        if (!tryConsume(expected)) {
            throw exception(errorMessage, null);
        }
        return currentType;
    }

    public TokenType consume(String expected) {
        return consume(expected, "Expected: '" + expected + "'.");
    }

    public String consumeIdentifier() {
        return consumeIdentifier("Identifier expected!");
    }

    public String consumeIdentifier(String errorMessage) {
        if (currentType != TokenType.IDENTIFIER) {
            throw exception(errorMessage, null);
        }
        String identifier = currentValue;
        nextToken();
        return identifier;
    }

    public ParsingException exception(String message, Exception cause) {
        return new ParsingException(
                message, cause);
    }

    protected boolean insertSemicolon() {
        return (currentType == TokenType.IDENTIFIER || currentType == TokenType.NUMBER ||
                currentType == TokenType.STRING  ||
                (currentValue.length() == 1 && ")]}".indexOf(currentValue) != -1));
    }

    public String consumeComments() {
        String result = skippedComments.toString();
        skippedComments.setLength(0);
        return result;
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
                skippedComments.append(value.trim() + "\n");
                System.out.println("Comment: " + value);
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

    @Override
    public String toString() {
        return currentType + " " + currentValue + " position: " + currentPosition;
    }

    public boolean tryConsume(String value) {
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
                "apply(" + base+ ", " + bracket + ", " + arguments + ")");
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

    public T suffixOperator(Tokenizer tokenizer, String name, T argument) {
        throw new UnsupportedOperationException("suffixOperator(" + name + ", " + argument + ')');
    }

    public T stringLiteral(Tokenizer tokenizer, String value) {
        throw new UnsupportedOperationException("stringLiteral(" + value + ')');
    }

    public T ternaryOperator(Tokenizer tokenizer, String operator, T left, T middle, T right) {
        throw new UnsupportedOperationException("ternaryOperator(" + operator + ')');
    }
}

class ExpressionParser<T> {

    private final HashMap<String,Symbol> prefix = new HashMap<>();
    private final HashMap<String,Symbol> infix = new HashMap<>();
    private final HashSet<String> otherSymbols = new HashSet<>();
    private final HashSet<String> primary = new HashSet<>();
    private final HashMap<String, String[]> calls = new HashMap<>();
    private final HashMap<String, String[]> groups = new HashMap<>();

    private final Parser<T> parser;
    private int strongImplicitOperatorPrecedence = -1;
    private int weakImplicitOperatorPrecedence = -1;

    public ExpressionParser(Parser<T> parser) {
        this.parser = parser;
    }

    /**
     * Adds "apply" brackets with the given precedence. Used for function calls or array element access.
     */
    public void addApplyBrackets(int precedence, String open, String separator, String close) {
        infix.put(open, new Symbol(precedence, separator, close));
        if (separator != null) {
            otherSymbols.add(separator);
        }
        otherSymbols.add(close);
    }

    /**
     * Adds "call" brackets, parsed eagerly after identifiers.
     */
    public void addCallBrackets(String open, String separator, String close) {
        calls.put(open, new String[]{separator, close});
        otherSymbols.add(open);
        if (separator != null) {
            otherSymbols.add(separator);
        }
        otherSymbols.add(close);
    }

    /**
     * Adds grouping. If the separator is null, only a single element will be permitted.
     * If the separator is empty, whitespace will be sufficient for element
     * separation. Used for parsing lists or overriding the operator precedence (typically with
     * parens and a null separator).
     */
    public void addGroupBrackets(String open, String separator, String close) {
        groups.put(open, new String[] {separator, close});
        otherSymbols.add(open);
        if (separator != null) {
            otherSymbols.add(separator);
        }
        otherSymbols.add(close);
    }

    public void addPrimary(String... names) {
        for (String name : names) {
            primary.add(name);
        }
    }

    public void addTernaryOperator(int precedence, String primaryOperator, String secondaryOperator) {
        infix.put(primaryOperator, new Symbol(precedence, secondaryOperator, null));
        otherSymbols.add(secondaryOperator);
    }

    /**
     * Add prefixOperator, infixOperator or postfix operators with the given precedence.
     */
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

    /**
     * Returns all symbols registered via add...Operator and add...Bracket calls.
     * Useful for tokenizer construction.
     */
    public Iterable<String> getSymbols() {
        HashSet<String> result = new HashSet<>();
        result.addAll(otherSymbols);
        result.addAll(infix.keySet());
        result.addAll(prefix.keySet());
        result.addAll(primary);
        return result;
    }

    /**
     * Parser the given expression using a simple StreamTokenizer-based parser.
     * Leftover tokens will cause an exception.
     */
    public T parse(String expr) {
        Tokenizer tokenizer = new Tokenizer(new Scanner(expr), getSymbols());
        tokenizer.nextToken();
        T result = parse(tokenizer);
        if (tokenizer.currentType != Tokenizer.TokenType.EOF) {
            throw tokenizer.exception("Leftover input.", null);
        }
        return result;
    }

    /**
     * Parser an expression from the given tokenizer. Leftover tokens will be ignored and
     * may be handled by the caller.
     */
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

        while(true) {
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
                        tokenizer.consume(symbol.separator);
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
                        case SUFFIX:
                            left = parser.suffixOperator(tokenizer, token, left);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                }
            }
        }
        return left;
    }


    // Precondition: Opening paren consumed
    // Postcondition: Closing paren consumed
    List<T> parseList(Tokenizer tokenizer, String separator, String close) {
        ArrayList<T> elements = new ArrayList<>();
        if (!tokenizer.currentValue.equals(close)) {
            while (true) {
                elements.add(parse(tokenizer));
                String op = tokenizer.currentValue;
                if (op.equals(close)) {
                    break;
                }
                if (separator == null) {
                    throw tokenizer.exception("Closing bracket expected: '" + close + "'.", null);
                }
                if (!separator.isEmpty()) {
                    if (!op.equals(separator)) {
                        throw tokenizer.exception("List separator '" + separator + "' or closing paren '"
                                + close + " expected.", null);
                    }
                    tokenizer.nextToken();  // separator
                }
            }
        }
        tokenizer.nextToken();  // closing paren
        return elements;
    }

    T parsePrimary(Tokenizer tokenizer) {
        String candidate = tokenizer.currentValue;
        if (groups.containsKey(candidate)) {
            tokenizer.nextToken();
            String[] grouping = groups.get(candidate);
            return parser.group(tokenizer, candidate, parseList(tokenizer, grouping[0], grouping[1]));
        }

        if (primary.contains(candidate)) {
            tokenizer.nextToken();
            return parser.primary(tokenizer, candidate);
        }

        T result;
        switch (tokenizer.currentType) {
            case NUMBER:
                tokenizer.nextToken();
                result = parser.numberLiteral(tokenizer, candidate);
                break;
            case IDENTIFIER:
                tokenizer.nextToken();
                if (calls.containsKey(tokenizer.currentValue)) {
                    String openingBracket = tokenizer.currentValue;
                    String[] call = calls.get(openingBracket);
                    tokenizer.nextToken();
                    result = parser.call(tokenizer, candidate, openingBracket, parseList(tokenizer, call[0], call[1]));
                } else {
                    result = parser.identifier(tokenizer, candidate);
                }
                break;
            case STRING:
                tokenizer.nextToken();
                result = parser.stringLiteral(tokenizer, candidate);
                break;
            default:
                throw tokenizer.exception("Unexpected token type.", null);
        }
        return result;
    }


}

