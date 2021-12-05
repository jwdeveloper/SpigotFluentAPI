package jw.spigot_fluent_api.dependency_injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpigotBean
{
    InjectionType injectionType() default InjectionType.SINGLETON;

    boolean lazyLoad() default true;
}
