package syntacticanalyzer;

public class SyntaxError extends Exception {
    private String error;

    public SyntaxError(String error) {
        super(error);
    }
}
