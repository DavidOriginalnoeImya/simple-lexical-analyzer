package ver3;

import ver3.pair.Lexeme;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private final List<Lexeme> lexemes = new ArrayList<>();

    private final Map<String, List<String>> lexemeTable = new LinkedHashMap<>();

    private final String prePattern = "(?<![^\\(\\[{\\s])", postPattern = "(?![^\\)\\]};,\\s])";

    public LexicalAnalyzer() {
        initLexemeTable();
    }

    public List<Lexeme> getLexemes(String sourceCode) {
        analyze(sourceCode);

        return lexemes;
    }

    private void analyze(String sourceCode) {
        for (Map.Entry<String, List<String>> entry: lexemeTable.entrySet()) {
            //System.out.println(sourceCode);
            for (String lexemePattern: entry.getValue()) {
                Pattern pattern = Pattern.compile(lexemePattern);
                Matcher matcher = pattern.matcher(sourceCode);

                while (matcher.find()) {
                    lexemes.add(getLexemeAddPos(matcher.end()),
                            new Lexeme(matcher.start(), matcher.end(),
                                    matcher.group().trim() + " : " + entry.getKey()));
                    sourceCode = sourceCode.substring(0, matcher.start()) +
                            Character.toString((char)0).repeat(matcher.end() - matcher.start()) +
                            sourceCode.substring(matcher.end());

                    //System.out.println(matcher.start() + "," + matcher.end() + " " + matcher.group() + " : " + entry.getKey());
                }
            }
        }
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
//        lexemeTable.put("строковый литерал", List.of("(?<![^\\(\\[{\\s=])\".*?\"(?![^\\)\\]};,\\s=])"));
//        lexemeTable.put("ключевое слово", List.of("(?<![A-Za-z\\d])if(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])for(?![A-Za-z\\d])", "(?<![A-Za-z\\d])while(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])private(?![A-Za-z\\d])", "(?<![A-Za-z\\d])public(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])static(?![A-Za-z\\d])", "(?<![A-Za-z\\d])main(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])class(?![A-Za-z\\d])"));
//        lexemeTable.put("идентификатор типа", List.of("(?<![A-Za-z\\d])int *(\\[])?(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])char *(\\[])?(?![A-Za-z\\d])", "(?<![A-Za-z\\d])double *(\\[])?(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])boolean *(\\[])?(?![A-Za-z\\d])", "(?<![A-Za-z\\d])String *(\\[])?(?![A-Za-z\\d])",
//                "(?<![A-Za-z\\d])void(?![A-Za-z\\d])"));
//        lexemeTable.put("идентификатор", List.of("(?<![\\da-zA-Z])([a-zA-Z$_][a-zA-Z0-9_$]*)"));
//        lexemeTable.put("арифметический оператор", List.of("\\+", "-", "\\*", "%"));
//        lexemeTable.put("оператор сравнения", List.of("==", "!=", ">=", "<=", ">", "<"));
//        lexemeTable.put("оператор присваивания", List.of("="));
//        lexemeTable.put("вещественный литерал", List.of("(?<![\\da-zA-Z])\\d*\\.\\d+(?![A-Za-z\\d])"));
//        lexemeTable.put("целочисленный литерал", List.of("(?<![\\da-zA-Z])\\d+(?![A-Za-z\\d])"));
//        lexemeTable.put("разделитель", List.of("\\(", "\\)", "\\{", "}", ";"));
//        lexemeTable.put("неизвестная лексема", List.of("[^\\s" + (char)0 + "]+"));


        lexemeTable.put("строковый литерал", List.of("\".*?\""));
        lexemeTable.put("ключевое слово", List.of("if", "for", "while", "private", "public", "static", "main", "class"));
        lexemeTable.put("идентификатор типа", List.of("int *(\\[])?", "char *(\\[])?",
                "double *(\\[])?", "boolean *(\\[])?", "String *(\\[])?", "void"));
        lexemeTable.put("идентификатор", List.of("[a-zA-Z$_][a-zA-Z0-9_$]*"));
        lexemeTable.put("арифметический оператор", List.of("\\+", "-", "\\*", "/", "%"));
        lexemeTable.put("оператор сравнения", List.of("==", "!=", ">=", "<=", ">", "<"));
        lexemeTable.put("оператор присваивания", List.of("="));
        lexemeTable.put("вещественный литерал", List.of("\\d*\\.\\d+"));
        lexemeTable.put("целочисленный литерал", List.of("\\d+"));
        lexemeTable.put("разделитель", List.of("\\(", "\\)", "\\{", "}", ";"));
        lexemeTable.put("неизвестная лексема", List.of("[^\\s" + (char)0 + "]+"));
    }
}
