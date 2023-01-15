package jw.fluent.api.web_socket.annotations;

import jw.fluent.api.utilites.java.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PacketVersion
{
    String pluginVersion() default "";
}