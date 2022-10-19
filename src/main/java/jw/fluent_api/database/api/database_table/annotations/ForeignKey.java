package jw.fluent_api.database.api.database_table.annotations;

import jw.fluent_api.database.api.database_table.enums.ReferenceOption;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ForeignKey
{
    String columnName() default "";

    String referencedColumnName() default "id";

    ReferenceOption onDelete() default ReferenceOption.SET_NULL;

    ReferenceOption onUpdate() default ReferenceOption.NO_ACTION;
}
