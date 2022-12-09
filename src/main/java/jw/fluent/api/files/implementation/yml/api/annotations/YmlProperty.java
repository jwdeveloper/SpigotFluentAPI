package jw.fluent.api.files.implementation.yml.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface YmlProperty {

    String name() default "";

    String path() default "";

    String description() default "";

    boolean required() default false;
}
