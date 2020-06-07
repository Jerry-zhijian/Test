package c.p.b.api.exception;

/**
 * Created 2018-09-18 15:09:24
 *
 * @author Michael.Zhang
 */
public class UnauthorizedException extends ErrorCodeException {

    public UnauthorizedException(int code) {
        super(code);
    }

    public UnauthorizedException(int code, String message) {
        super(code, message);
    }

    public UnauthorizedException(int code, Throwable cause) {
        super(code, cause);
    }

    public UnauthorizedException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
