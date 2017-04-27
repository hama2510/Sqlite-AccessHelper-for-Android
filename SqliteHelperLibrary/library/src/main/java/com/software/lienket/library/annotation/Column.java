package com.software.lienket.library.annotation;

/**
 * Created by KIEN on 4/20/2017.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.software.lienket.library.constant.FieldType.NOT_NULL;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public String name();

    public String fieldType() default NOT_NULL;
}
