package recyclemug.ProjectMug.exception;

public class NoCupsForReturnException extends RuntimeException{
    public NoCupsForReturnException() {
        super();
    }

    public NoCupsForReturnException(String message) {
        super(message);
    }

    public NoCupsForReturnException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCupsForReturnException(Throwable cause) {
        super(cause);
    }

    protected NoCupsForReturnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
