package jw.spigot_fluent_api.fluent_validator.api;

import jw.spigot_fluent_api.utilites.ActionResult;

import java.lang.reflect.Field;

public interface ValidationAction<T>
{
     ActionResult check(T object, Field field) throws IllegalAccessException;
}
