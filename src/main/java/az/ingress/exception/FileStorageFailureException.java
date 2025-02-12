package az.ingress.exception;

public class FileStorageFailureException extends RuntimeException{
    public FileStorageFailureException(String message) {
        super(message);
    }
}
