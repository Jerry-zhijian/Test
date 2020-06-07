package c.p.b.api.exception;

/**
 * This class defined a business exception with code and message.
 * <p>
 * Created: 2017-09-10 20:18:02
 *
 * @author Michael.Zhang
 */
public class BusinessException extends ErrorCodeException {

    public BusinessException(int code, String message) {
        super(code, message);
    }

}