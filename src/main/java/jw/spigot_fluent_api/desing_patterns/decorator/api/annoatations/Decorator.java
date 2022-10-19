package jw.spigot_fluent_api.desing_patterns.decorator.api.annoatations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Decorator
{
    Class<?> target() default Object.class;
}
