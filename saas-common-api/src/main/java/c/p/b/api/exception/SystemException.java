package c.p.b.api.exception;

/**
 * This class defined a system exception with code and message.
 * <p>
 * Created: 2017-09-10 20:20:18
 *
 * @author Michael.Zhang
 */
public class SystemException extends ErrorCodeException {

    /**
     * Default response code of system exception.
     */
    public static final int DEFAULT_RESPONSE_CODE = -1;

    public SystemException(String message) {
        this(DEFAULT_RESPONSE_CODE, message);
    }

    public SystemException(int code, String message) {
        this(code, message, null);
    }

    public SystemException(int code, Throwable cause) {
        this(code, cause == null ? null : cause.getMessage(), cause);
    }

    public SystemException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
