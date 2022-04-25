package ver3.pair;

public class Lexeme {
    private Integer start = 0, end = 0;
    private String lexeme = "";

    public Lexeme() {}

    public Lexeme(Integer start, Integer end, String lexeme) {
        this.start = start;
        this.end = end;
        this.lexeme = lexeme;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }
}
