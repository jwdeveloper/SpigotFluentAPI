package jw.spigot_fluent_api.simple_commands.annotations;

import jw.spigot_fluent_api.simple_commands.enums.AccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Command
{
    String name() default "";

    String description() default "";

    AccessType access() default AccessType.COMMAND_SENDER;

    boolean requireAllParameters() default true;
}
