package c.p.b.api.packaging;

import java.lang.annotation.*;

/**
 * The annotation for response auto packaging.
 * 
 * Created: 2018-08-13 10:40:04
 * 
 * @author  Michael.Zhang
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponsePackaging {
}
