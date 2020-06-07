package c.p.b.api.exception;

/**
 * Created 2018-09-20 19:37:17
 *
 * @author Michael.Zhang
 */
public class ForbiddenException extends ErrorCodeException {

    public ForbiddenException(int code) {
        super(code);
    }

    public ForbiddenException(int code, String message) {
        super(code, message);
    }

    public ForbiddenException(int code, Throwable cause) {
        super(code, cause);
    }

    public ForbiddenException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
