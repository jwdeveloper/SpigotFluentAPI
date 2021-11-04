package jw.spigot_fluent_api.data_models.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Repeatable(PermissionContainer.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission
{
    String value() default "";
}


