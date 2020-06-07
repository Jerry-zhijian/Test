package c.b.s.common.util.domain;

import java.io.Serializable;

/**
 * 
 * 
 * Created: 2018-05-22 21:35:30
 * 
 * @author  Michael.Zhang
 */
public abstract class IdentifiedDomainObject implements Serializable {

    private long id;

    protected long id() {
        return this.id;
    }

    protected void setId(long id) {
        this.id = id;
    }

}
