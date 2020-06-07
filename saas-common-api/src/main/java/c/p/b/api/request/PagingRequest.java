package c.p.b.api.request;

import javax.validation.constraints.Min;

/**
 * Base paging request.
 * <p>
 * Created: 2018-08-01 15:04:12
 *
 * @author Michael.Zhang
 */
public class PagingRequest extends Request {
    @Min(value = 1, message = "页大小必须大于0")
    private int pageNum = DEFAULT_PAGE_NUM;
    @Min(value = 1, message = "每页大小必须大于0")
    private int pageSize = DEFAULT_PAGE_SIZE;
    private String orderByColumn;
    private String orderByType = DEFAULT_ORDER_BY_TYPE;


    /**
     * The default current page number when <p>pageNum</p> is not set.
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    /**
     * The default page size when <p>pageSize</p> is not set.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * The default order by type when <p>orderByType</p> is not set.
     */
    private static final String DEFAULT_ORDER_BY_TYPE = "DESC";


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }

}