package exceptions;

public class WrongRepositoryTypeException extends RuntimeException {

    public WrongRepositoryTypeException(String repository) {
        super("Wrong type provided for the build of the " + repository + " repository");
    }
}
