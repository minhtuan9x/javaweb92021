package com.laptrinhjavaweb.anotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)///danh dau anotation
@Target(ElementType.TYPE)
public @interface SearchObject {
    String tableName() default "";
    String aliasValue() default "";
    boolean groupBy() default false;
}
