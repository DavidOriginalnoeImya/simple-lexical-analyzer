import lexicalanalyzer.LexicalAnalyzer;
import lexicalanalyzer.pair.Lexeme;
import syntacticanalyzer.SyntacticAnalyzer;
import syntacticanalyzer.SyntaxError;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MainClass {

    public static String getFileContent(String fileName) {
        StringBuilder fileContent = new StringBuilder();

        try (Scanner scanner = new Scanner(new FileReader(fileName))){
            while (scanner.hasNext()) {
                fileContent.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fileContent.toString();
    }

    public static void main(String[] args) {
        String fileContent = getFileContent("input.txt");

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

        List<Lexeme> lexemes = lexicalAnalyzer.getLexemes(fileContent);

        for (Lexeme lexeme: lexemes) {
            System.out.println(lexeme.getLexeme().split(":")[1].trim());
        }

        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(new ArrayDeque<>(lexemes));

        try {
            syntacticAnalyzer.analyze();
        }
        catch (SyntaxError e) {
            System.out.println(e.getMessage());
        }
    }
}
