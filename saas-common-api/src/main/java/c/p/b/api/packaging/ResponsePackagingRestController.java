package c.p.b.api.packaging;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Rest controller annotation with response auto packaging.
 * 
 * Created: 2018-08-13 13:36:32
 * 
 * @author  Michael.Zhang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface ResponsePackagingRestController {
}