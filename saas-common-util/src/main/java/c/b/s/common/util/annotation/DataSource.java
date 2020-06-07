package c.b.s.common.util.annotation;

import c.b.s.common.util.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * @author chengzhijian
 * @title: DataSource
 * @projectName saas-platform
 * @date 2020/5/2015:09
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /**
     * 切换数据源名称
     */
    DataSourceType value() default DataSourceType.MASTER;
}
