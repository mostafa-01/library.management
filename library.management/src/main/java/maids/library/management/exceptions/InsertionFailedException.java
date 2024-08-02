package maids.library.management.exceptions;

public class InsertionFailedException extends RuntimeException{
    public InsertionFailedException() {
    }

    public InsertionFailedException(String message) {
        super(message);
    }
}
