package recyclemug.ProjectMug.exception;

public class CustomerStateNotAllowedException extends RuntimeException{
    public CustomerStateNotAllowedException() {
        super();
    }

    public CustomerStateNotAllowedException(String message) {
        super(message);
    }

    public CustomerStateNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerStateNotAllowedException(Throwable cause) {
        super(cause);
    }

    protected CustomerStateNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
