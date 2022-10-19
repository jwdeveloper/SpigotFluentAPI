package jw.fluent_api.utilites.files.yml.api.annotations;

import jw.fluent_api.desing_patterns.unit_of_work.api.annotation.files.TextFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TextFile
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface YmlFile {

    String fileName() default "";

    String globalPath() default "";
}
