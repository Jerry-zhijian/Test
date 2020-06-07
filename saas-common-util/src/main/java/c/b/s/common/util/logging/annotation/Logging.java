package c.b.s.common.util.logging.annotation;

import java.lang.annotation.*;

/**
 * Created by guiqingqing on 2018/8/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Logging {
    String code() default "";
}