package lexicalanalyzer;

import lexicalanalyzer.pair.Lexeme;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private final List<Lexeme> lexemes = new ArrayList<>();

    private final Map<String, List<String>> lexemeTable = new LinkedHashMap<>();

    public LexicalAnalyzer() {
        initLexemeTable();
    }

    public List<Lexeme> getLexemes(String sourceCode) {
        analyze(sourceCode);

        return lexemes;
    }

    private void analyze(String sourceCode) {
        for (Map.Entry<String, List<String>> entry: lexemeTable.entrySet()) {
            for (String lexemePattern: entry.getValue()) {
                Pattern pattern = Pattern.compile(lexemePattern);
                Matcher matcher = pattern.matcher(sourceCode);

                while (matcher.find()) {
                    lexemes.add(getLexemeAddPos(matcher.end()), new Lexeme(matcher.start(), matcher.end(), entry.getKey()));
                    sourceCode = sourceCode.substring(0, matcher.start()) +
                            Character.toString((char)0).repeat(matcher.end() - matcher.start()) +
                            sourceCode.substring(matcher.end());
                }
            }
        }
    }

    public Deque<Lexeme> getLexemeDeque() {
        return new ArrayDeque<>(lexemes);
    }

    public int getLexemeAddPos(int lexemeEnd) {
        for (int index = 0; index < lexemes.size(); ++index) {
            if (lexemeEnd <= lexemes.get(index).getStart()) {
                return index;
            }
        }

        return lexemes.size();
    }

    private void initLexemeTable() {
        lexemeTable.put("строковый литерал", List.of("\".*?\""));
        lexemeTable.put("class", List.of("class"));
        lexemeTable.put("ключевое слово", List.of("if", "for", "while", "private", "public", "static", "main"));
        lexemeTable.put("идентификатор типа", List.of("int *(\\[])?", "char *(\\[])?",
                "double *(\\[])?", "boolean *(\\[])?", "String *(\\[])?", "void"));
        lexemeTable.put("идентификатор", List.of("[a-zA-Z$_][a-zA-Z0-9_$]*"));
        lexemeTable.put("арифметический оператор", List.of("\\+", "-", "\\*", "/", "%"));
        lexemeTable.put("оператор сравнения", List.of("==", "!=", ">=", "<=", ">", "<"));
        lexemeTable.put("=", List.of("="));
        lexemeTable.put("вещественный литерал", List.of("\\d*\\.\\d+"));
        lexemeTable.put("целочисленный литерал", List.of("\\d+"));
        lexemeTable.put("{", List.of("\\{")); lexemeTable.put("}", List.of("}"));
        lexemeTable.put("(", List.of("\\(")); lexemeTable.put(")", List.of("\\)"));
        lexemeTable.put(";", List.of(";")); lexemeTable.put(",", List.of(","));
        lexemeTable.put("неизвестная лексема", List.of("[^\\s" + (char)0 + "]+"));
    }
}
