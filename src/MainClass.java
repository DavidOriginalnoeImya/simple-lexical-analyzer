import ver3.LexicalAnalyzer;
import ver3.pair.Lexeme;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            System.out.println(lexeme.getLexeme());
        }
    }
}
