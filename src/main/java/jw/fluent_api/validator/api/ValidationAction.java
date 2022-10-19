package jw.fluent_api.validator.api;

import jw.fluent_api.utilites.ActionResult;

import java.lang.reflect.Field;

public interface ValidationAction<T>
{
     ActionResult check(T object, Field field) throws IllegalAccessException;
}
