package exceptions;

public class WrongActionTypeException extends RuntimeException {

    public WrongActionTypeException() {
        super("Wrong type provided for audit service");
    }
}
