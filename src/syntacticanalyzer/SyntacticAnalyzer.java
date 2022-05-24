package syntacticanalyzer;

import lexicalanalyzer.LexicalAnalyzer;
import lexicalanalyzer.pair.Lexeme;

import java.util.Deque;
import java.util.List;

public class SyntacticAnalyzer {
    private Deque<Lexeme> lexemes;

    private String nextLexeme = "";

    public SyntacticAnalyzer(Deque<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public void analyze() throws SyntaxError {
        classAnalyzer();
    }

    private void classAnalyzer() throws SyntaxError {
        String errorMessage = "Ошибка синтаксиса класса";

        nextLexeme = getNextLexeme();

        if ("class".equals(nextLexeme)) {
            nextLexeme = getNextLexeme();

            if ("идентификатор".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();

                if ("{".equals(nextLexeme)) {

                    methodAnalyzer();

                    if (!"}".equals(nextLexeme)) {
                        throw new SyntaxError(errorMessage);
                    }
                }
                else {
                    throw new SyntaxError(errorMessage);
                }
            }
            else {
                throw new SyntaxError(errorMessage);
            }
        }
        else {
            throw new SyntaxError(errorMessage);
        }
    }

    private void methodAnalyzer() throws SyntaxError {
        String errorMessage = "Ошибка синтаксиса метода";

        nextLexeme = getNextLexeme();

        while ("идентификатор типа".equals(nextLexeme)) {
            nextLexeme = getNextLexeme();

            if ("идентификатор".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();

                if ("(".equals(nextLexeme)) {
                    nextLexeme = getNextLexeme();

                    if (!")".equals(nextLexeme)) {
                        methodParametersAnalyzer();

                        if (")".equals(nextLexeme)) {
                            nextLexeme = getNextLexeme();

                            methodBodyAnalyzer();
                        }
                        else throw new SyntaxError(errorMessage);
                    }
                    else {
                        nextLexeme = getNextLexeme();

                        methodBodyAnalyzer();
                    }
                }
                else throw new SyntaxError(errorMessage);
            }
            else throw new SyntaxError(errorMessage);

            nextLexeme = getNextLexeme();
        }
    }

    private void methodParametersAnalyzer() throws SyntaxError {
        boolean isNextLexemeComma = true;

        String errorMessage = "Ошибка синтаксиса аргументов метода";

        do {
            if ("int".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if ("идентификатор".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if (",".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else {
                isNextLexemeComma = false;
            }

        } while (isNextLexemeComma);
    }

    private void methodBodyAnalyzer() throws SyntaxError {
        String errorMessage = "Синтаксическая ошибка в теле метода";

        if ("{".equals(nextLexeme)) {
            nextLexeme = getNextLexeme();

            if (!"}".equals(nextLexeme)) {
                statementsAnalyzer();

                if (!"}".equals(nextLexeme)) {
                    throw new SyntaxError(errorMessage);
                }
            }
        }
        else throw new SyntaxError(errorMessage);
    }

    private void statementsAnalyzer() throws SyntaxError {
        String errorMessage = "Синтаксическая ошибка в объявлении переменной";

        boolean isNextLexemeTypeIdent = true;

        do {
            if ("int".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if ("идентификатор".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if ("=".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if ("целочисленный литерал".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if (";".equals(nextLexeme)) {
                nextLexeme = getNextLexeme();
            }
            else throw new SyntaxError(errorMessage);

            if (!"int".equals(nextLexeme)) {
                isNextLexemeTypeIdent = false;
            }

        } while (isNextLexemeTypeIdent);
    }

    private String getNextLexeme() throws SyntaxError {
        if (lexemes.size() > 0) {
            return lexemes.pollFirst().getLexeme();
        }

        throw new SyntaxError("Синтаксическая ошибка");
    }
}
