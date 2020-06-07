package c.p.b.api.response;

/**
 * Base response.
 * <p>
 * Created: 2018-05-22 22:08:51
 *
 * @author Michael.Zhang
 */
public class Response<T> extends MessageResponse {

    private int code;
    private T result;

    public static final int DEFAULT_SUCCESS_CODE = 0;
    public static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    public static final int DEFAULT_ERROR_CODE = -1;
    public static final String DEFAULT_ERROR_MESSAGE = "ERROR";
    public static final Object DEFAULT_RESULT = null;


    public Response() {
    }

    public Response(int code, String message, T result) {
        super(message);
        this.code = code;
        this.result = result;
    }

    public static Response ok() {
        return Response.ok(DEFAULT_SUCCESS_CODE);
    }

    public static Response ok(int code) {
        return Response.ok(code, DEFAULT_SUCCESS_MESSAGE);
    }

    public static Response ok(String message) {
        return Response.ok(DEFAULT_SUCCESS_CODE, message);
    }

    public static Response ok(Object result) {
        return Response.ok(DEFAULT_SUCCESS_CODE, result);
    }

    public static Response ok(int code, String message) {
        return Response.ok(code, message, DEFAULT_RESULT);
    }

    public static <T> Response ok(int code, T result) {
        return Response.ok(code, DEFAULT_SUCCESS_MESSAGE, result);
    }

    public static Response ok(String message, Object result) {
        return Response.ok(DEFAULT_SUCCESS_CODE, message, result);
    }

    public static <T> Response ok(int code, String message, T result) {
        return new Response(code, message, result);
    }


    public static Response err() {
        return Response.err(DEFAULT_ERROR_CODE);
    }

    public static Response err(int code) {
        return Response.err(code, DEFAULT_ERROR_MESSAGE);
    }

    public static Response err(String message) {
        return Response.err(DEFAULT_ERROR_CODE, message);
    }

    public static <T> Response err(int code, String message) {
        return new Response(code, message, DEFAULT_RESULT);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}