package jw.fluent.api.spigot.permissions.api.annotations;

import jw.fluent.api.utilites.java.StringUtils;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
@Repeatable(PermissionGroupValues.class)
public @interface PermissionGroup {
    String group() default "";
    String title() default "";
}