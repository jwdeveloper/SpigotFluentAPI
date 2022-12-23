package jw.fluent.api.desing_patterns.dependecy_injection.api.annotations;


import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Injection
{
    /*
     LifeTime:
         Singletone -> there will be only one instance of an object in Dependency Injection
         Transient -> every time object is need new instance is created
   */
    LifeTime lifeTime() default LifeTime.SINGLETON;

    /*
       Lazy load:
           true: -> object is initialized after calling it from Dependency Injection container
           false: ->  object is initialized when application starts
     */
    boolean lazyLoad() default true;
    boolean ignoreInterface() default true;
    Class<?> toInterface() default Object.class;
}
