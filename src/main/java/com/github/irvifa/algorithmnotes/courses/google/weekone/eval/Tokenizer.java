//package com.github.irvifa.algorithmnotes.courses.google.weekone.eval;
//
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Scanner;
//import java.util.TreeSet;
//import java.util.regex.Pattern;
//
//
//public class Tokenizer {
//    public static final Pattern DEFAULT_NUMBER_PATTERN = Pattern.compile(
//            "\\G\\s*(\\d+(\\.\\d*)?|\\.\\d+)([eE][+-]?\\d+)?");
//
//    public static final Pattern DEFAULT_IDENTIFIER_PATTERN = Pattern.compile(
//            "\\G\\s*[\\p{Alpha}_$][\\p{Alpha}_$\\d]*");
//
//    public static final Pattern DEFAULT_STRING_PATTERN = Pattern.compile(
//            "\\G\\s*(\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(\\\\.[^'\\\\]*)*)')");
//    public static final Pattern DEFAULT_END_PATTERN = Pattern.compile("\\G\\s*\\Z");
//
//    public static final Pattern DEFAULT_LINE_COMMENT_PATTERN = Pattern.compile("\\G\\h*#.*(\\v|\\Z)");
//
//    public static final Pattern DEFAULT_NEWLINE_PATTERN = Pattern.compile("\\G\\h*\\v");
//
//    public enum TokenType {
//        UNRECOGNIZED, BOF, IDENTIFIER, SYMBOL, NUMBER, STRING, EOF
//    }
//
//    public Pattern numberPattern = DEFAULT_NUMBER_PATTERN;
//    public Pattern identifierPattern = DEFAULT_IDENTIFIER_PATTERN;
//    public Pattern stringPattern = DEFAULT_STRING_PATTERN;
//    public Pattern endPattern = DEFAULT_END_PATTERN;
//    public Pattern newlinePattern = DEFAULT_NEWLINE_PATTERN;
//    public Pattern lineCommentPattern = DEFAULT_LINE_COMMENT_PATTERN;
//    public Pattern symbolPattern;
//
//    public int currentLine = 1;
//    public int lastLineStart = 0;
//    public int currentPosition = 0;
//    public String currentValue = "";
//    public TokenType currentType = TokenType.BOF;
//    public String leadingWhitespace = "";
//    public boolean insertSemicolons;
//
//    protected final Scanner scanner;
//
//    public Tokenizer(Scanner scanner, Iterable<String> symbols, String... additionalSymbols) {
//        this.scanner = scanner;
//        StringBuilder sb = new StringBuilder("\\G\\s*(");
//
//        TreeSet<String> sortedSymbols = new TreeSet<>(new Comparator<String>() {
//            @Override
//            public int compare(String s1, String s2) {
//                int dl = -Integer.compare(s1.length(), s2.length());
//                return dl == 0 ? s1.compareTo(s2) : dl;
//            }
//        });
//        for (String symbol: symbols) {
//            sortedSymbols.add(symbol);
//        }
//        Collections.addAll(sortedSymbols, additionalSymbols);
//        for (String s : sortedSymbols) {
//            sb.append(Pattern.quote(s));
//            sb.append('|');
//        }
//        sb.setCharAt(sb.length() - 1, ')');
//        symbolPattern = Pattern.compile(sb.toString());
//    }
//
//    public TokenType next(String expected, String errorMessage) {
//        if (!hasNext(expected)) {
//            throw exceptionBuilder(errorMessage, null);
//        }
//        return currentType;
//    }
//
//    public TokenType next(String expected) {
//        return next(expected, "Expected: '" + expected + "'.");
//    }
//
//    public ParsingException exceptionBuilder(String message, Exception cause) {
//        return new ParsingException(
//                message, cause);
//    }
//
//    protected boolean insertSemicolon() {
//        return (currentType == TokenType.IDENTIFIER || currentType == TokenType.NUMBER ||
//                currentType == TokenType.STRING  ||
//                (currentValue.length() == 1 && ")]}".contains(currentValue)));
//    }
//
//    public void nextToken() {
//        currentPosition += currentValue.length();
//        String value;
//        if (scanner.ioException() != null) {
//            throw exceptionBuilder("IO Exception: " + scanner.ioException().getMessage(), scanner.ioException());
//        }
//
//        boolean newLine = false;
//        while (true) {
//            if ((value = scanner.findWithinHorizon(lineCommentPattern, 0)) != null) {
//                System.out.println("Comment: " + value);
//            } else if ((value = scanner.findWithinHorizon(newlinePattern, 0)) == null) {
//                break;
//            }
//            newLine = true;
//            currentPosition += value.length();
//            currentLine++;
//            lastLineStart = currentPosition;
//        }
//
//        if (newLine && insertSemicolons && insertSemicolon()) {
//            value = ";";
//            currentPosition--;
//            currentType = TokenType.SYMBOL;
//        } else if ((value = scanner.findWithinHorizon(identifierPattern, 0)) != null) {
//            currentType = TokenType.IDENTIFIER;
//        } else if ((value = scanner.findWithinHorizon(numberPattern, 0)) != null) {
//            currentType = TokenType.NUMBER;
//        } else if ((value = scanner.findWithinHorizon(stringPattern, 0)) != null) {
//            currentType = TokenType.STRING;
//        } else if ((value = scanner.findWithinHorizon(symbolPattern, 0)) != null) {
//            currentType = TokenType.SYMBOL;
//        } else if ((value = scanner.findWithinHorizon(endPattern, 0)) != null) {
//            currentType = TokenType.EOF;
//        } else if ((value = scanner.findWithinHorizon("\\G\\s*\\S*", 0)) != null) {
//            currentType = TokenType.UNRECOGNIZED;
//        } else {
//            currentType = TokenType.UNRECOGNIZED;
//            throw exceptionBuilder("EOF not reached, but catchall not matched.", null);
//        }
//        if (value.length() > 0 && value.charAt(0) <= ' ') {
//            currentValue = value.trim();
//            leadingWhitespace = value.substring(0, value.length() - currentValue.length());
//            int pos = 0;
//            while (true) {
//                int j = leadingWhitespace.indexOf('\n', pos);
//                if (j == -1) {
//                    break;
//                }
//                pos = j + 1;
//                currentLine++;
//                lastLineStart = currentPosition + j;
//            }
//            currentPosition += leadingWhitespace.length();
//        } else {
//            leadingWhitespace = "";
//            currentValue = value;
//        }
//    }
//
//    public boolean hasNext(String value) {
//        if (!currentValue.equals(value)) {
//            return false;
//        }
//        nextToken();
//        return true;
//    }
//}
