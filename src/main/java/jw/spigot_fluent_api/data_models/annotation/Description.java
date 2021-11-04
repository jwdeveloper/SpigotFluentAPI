package jw.spigot_fluent_api.data_models.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Repeatable(DescriptionContainer.class)
@Retention(RetentionPolicy.RUNTIME)
public  @interface Description
{
    public String value() default "";
}
