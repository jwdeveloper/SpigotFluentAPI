package jw.fluent_api.database.api.database_table.annotations;

import jw.fluent_api.database.mysql.utils.SqlTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column
{
    String name() default "";

    String type() default SqlTypes.VARCHAR;

    int size() default 15;


}
