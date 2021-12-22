package jw.spigot_fluent_api.simple_commands.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Repeatable(CommandPermissionContainer.class)
public @interface CommandPermission
{
    String permission() default "";
    boolean setGenericPermission() default false;
}
