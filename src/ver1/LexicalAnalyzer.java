package ver1;

import java.util.*;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private final Map<String, String> lexemeTable = new LinkedHashMap<>();

    public LexicalAnalyzer() {
        initLexemeTable();
    }

    public void analyze(String sourceCode) {
        List<String> splitters = new ArrayList<>();
        splitters.add("(?<===|\\(|\\)|\\{|}|;|<=|>=|!=)|(?===|\\(|\\)|\\{|}|;|<=|>=|!=)");
        splitters.add("(?<=[\\s=!><])|(?=[\\s=!><])");

        analyze(sourceCode, splitters, 0);
    }

    private void analyze(String sourceCode, List<String> splitters, int splitterNum) {
        if (splitters.size() > splitterNum) {
            String splitPattern = splitters.get(splitterNum);

            System.out.println(Arrays.toString(sourceCode.split(splitPattern)));

            for (String sourceCodePart : sourceCode.split(splitPattern)) {
                String lexeme = sourceCodePart.trim();

                if (lexeme.length() > 0) {
                    String lexemeType = checkLexemeType(lexeme);

                    if (lexemeType != null) {
                        System.out.println(lexeme + " : " + lexemeType);
                    }
                    else {
                        analyze(lexeme, splitters, splitterNum + 1);
                    }
                }
            }
        }
        else {
            System.out.println(sourceCode + " : неизвестная лексема");
        }
    }

    private String checkLexemeType(String lexeme) {
        for (Map.Entry<String, String> pair: lexemeTable.entrySet()) {
            if (Pattern.matches(pair.getKey(), lexeme)) {
                return pair.getValue();
            }
        }

        return null;
    }

    private void initLexemeTable() {
        lexemeTable.put("if|for|while|private|public|static|main|class", "ключевое слово");
        lexemeTable.put("int\\s*(\\[])?|char\\s*(\\[])?|double\\s*(\\[])?|boolean\\s*(\\[])?|String\\s*(\\[])?|void",
                "идентификатор типа");
        lexemeTable.put("[a-zA-Z$_][a-zA-Z0-9_$]*", "идентификатор");
        lexemeTable.put("\\+|-|\\*|/|%", "арифметический оператор");
        lexemeTable.put("==|!=|>|<|>=|<=", "оператор сравнения");
        lexemeTable.put("=", "оператор присваивания");
        lexemeTable.put("\\d+", "целочисленный литерал");
        lexemeTable.put("\".*\"", "строковый литерал");
        lexemeTable.put("\\d*\\.?\\d*", "вещественный литерал");
        lexemeTable.put("\\(|\\)|\\{|}|;|,", "разделитель");
    }
}
