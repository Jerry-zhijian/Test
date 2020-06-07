package c.p.b.api.exception;

/**
 * Error code exception.
 * <p>
 * Created: 2017-09-11 23:00:00
 *
 * @author Michael.Zhang
 */
public class ErrorCodeException extends NoStackTraceException {

    private int code;

    public ErrorCodeException(int code) {
        this(code, (String) null);
    }

    public ErrorCodeException(int code, String message) {
        this(code, message, null);
    }

    public ErrorCodeException(int code, Throwable cause) {
        this(code, cause == null ? null : cause.getMessage(), cause);
    }

    public ErrorCodeException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
