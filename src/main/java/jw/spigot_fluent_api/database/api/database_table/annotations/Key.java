package jw.spigot_fluent_api.database.api.database_table.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Key
{
    boolean isPrimary() default true;

    boolean autoIncrement() default true;
}
