package c.p.b.api.response;

/**
 * Base paging response.
 * <p>
 * Created: 2018-08-09 19:19:16
 *
 * @author Michael.Zhang
 */
public class PagingResponse extends Response {

    private int totalPageNum;
    private boolean isFirstPage;
    private boolean isLastPage;

    public PagingResponse(int code, String message, Object result) {
        super(code, message, result);
    }

}
