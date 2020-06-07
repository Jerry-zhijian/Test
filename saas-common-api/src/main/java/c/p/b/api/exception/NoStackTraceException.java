package c.p.b.api.exception;

/**
 * Not write stack trace runtime exception.
 * <p>
 * Created: 2018-08-10 16:26:21
 *
 * @author Michael.Zhang
 */
public class NoStackTraceException extends RuntimeException {

    public NoStackTraceException() {
        this((String) null);
    }

    public NoStackTraceException(String message) {
        this(message, null);
    }

    public NoStackTraceException(Throwable cause) {
        this(null, cause);
    }

    public NoStackTraceException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
