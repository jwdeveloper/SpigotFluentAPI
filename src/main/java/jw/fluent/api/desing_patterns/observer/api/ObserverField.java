package jw.fluent.api.desing_patterns.observer.api;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ObserverField
{

}

