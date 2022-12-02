package jw.fluent_api.spigot.permissions.api.annotations;

import jw.fluent_api.spigot.permissions.api.enums.Visibility;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(PermissionGroupValues.class)
public @interface PermissionGroup {
    String group() default "";
}