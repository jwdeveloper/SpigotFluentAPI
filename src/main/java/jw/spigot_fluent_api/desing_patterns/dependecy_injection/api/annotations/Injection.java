package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations;


import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Injection
{
    LifeTime lifeTime() default LifeTime.SINGLETON;
    boolean lazyLoad() default true;
}
