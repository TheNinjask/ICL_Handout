package parserExceptions;

public class TypeError extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TypeError(String msg) {
        super(msg);
    }
}