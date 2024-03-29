package jw.fluent.api.files.implementation.yaml_reader.api.annotations;

import jw.fluent.api.files.api.annotation.files.TextFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TextFile
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface YamlFile {

    String fileName() default "";

    String globalPath() default "";
}
