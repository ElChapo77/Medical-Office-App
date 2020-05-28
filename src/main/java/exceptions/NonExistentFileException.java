package exceptions;

public class NonExistentFileException extends RuntimeException {

    public NonExistentFileException(String file) {
        super(file + " doesn't exist!");
    }
}
