package jw.fluent.api.spigot.permissions.api.annotations;


import jw.fluent.api.spigot.permissions.api.enums.Visibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PermissionProperty {
    String description() default "";

    Visibility visibility() default Visibility.None;
}
