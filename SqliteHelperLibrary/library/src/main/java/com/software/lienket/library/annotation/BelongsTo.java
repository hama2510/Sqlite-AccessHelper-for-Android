package com.software.lienket.library.annotation;

import com.software.lienket.library.constant.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by KIEN on 4/20/2017.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BelongsTo {
    public Class table();

    public String updateAction() default ActionType.RESTRICT;

    public String deleteAction() default ActionType.RESTRICT;

}
