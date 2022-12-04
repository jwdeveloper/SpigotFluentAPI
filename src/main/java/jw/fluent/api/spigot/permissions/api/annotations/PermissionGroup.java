package jw.fluent.api.spigot.permissions.api.annotations;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(PermissionGroupValues.class)
public @interface PermissionGroup {
    String group() default "";
}