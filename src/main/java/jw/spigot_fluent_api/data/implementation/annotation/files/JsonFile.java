package jw.spigot_fluent_api.data.implementation.annotation.files;

import jw.spigot_fluent_api.database.api.database_table.annotations.Column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TextFile
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonFile  {
}