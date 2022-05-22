package syntacticanalyzer;

import lexicalanalyzer.LexicalAnalyzer;
import lexicalanalyzer.pair.Lexeme;

import java.util.Deque;
import java.util.List;

public class SyntacticAnalyzer {
    private Deque<Lexeme> lexemes;

    public SyntacticAnalyzer(Deque<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public void analyze() throws SyntaxError {
        classAnalyzer();
    }

    private void classAnalyzer() throws SyntaxError {
        String errorMessage = "Ошибка синтаксиса класса";

        if ("class".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
            if ("идентификатор".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                if ("{".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                    classBodyAnalyzer();

                    if (!"}".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                        throw new SyntaxError(errorMessage + 4);
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

    private void classBodyAnalyzer() throws SyntaxError {
        String errorMessage = "Ошибка синтаксиса тела класса";

        if ("идентификатор типа".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
            if ("идентификатор".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                Lexeme currentLexeme = getNextLexeme();

                if ("=".equals(currentLexeme.getLexeme().split(":")[1].trim())) {
                    if ("целочисленный литерал".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                        if (!";".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                            throw new SyntaxError(errorMessage);
                        }
                    }
                }
                else {
                    methodAnalyzer();
                }
            }
            else {
                throw new SyntaxError(errorMessage + 2);
            }
        }
        else {
            throw new SyntaxError(errorMessage + 3);
        }
    }

    private void methodAnalyzer() throws SyntaxError {
        String errorMessage = "Ошибка синтаксиса метода";

        if ("(".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
            methodParametersAnalyzer();

            if (")".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                if ("{".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                    if (!"}".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
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
    }

    private void methodParametersAnalyzer() throws SyntaxError {
        Lexeme lexeme;

        System.out.println("test");

        do {
            if ("идентификатор типа".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                if (!"идентификатор".equals(getNextLexeme().getLexeme().split(":")[1].trim())) {
                    throw new SyntaxError("Ошибка синтаксиса аргументов метода");
                }
            }
            else {
                throw new SyntaxError("Ошибка синтаксиса аргументов метода");
            }

            lexeme = getNextLexeme();

        } while (",".equals(lexeme.getLexeme().split(":")[1].trim()));

        lexemes.addFirst(lexeme);
    }

    private Lexeme getNextLexeme() throws SyntaxError {
        if (lexemes.size() > 0) {
            return lexemes.pollFirst();
        }

        throw new SyntaxError("Список лексем пуст");
    }
}
