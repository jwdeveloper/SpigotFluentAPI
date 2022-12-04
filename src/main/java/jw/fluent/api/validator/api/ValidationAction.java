package jw.fluent.api.validator.api;

import jw.fluent.api.utilites.ActionResult;

import java.lang.reflect.Field;

public interface ValidationAction<T>
{
     ActionResult check(T object, Field field) throws IllegalAccessException;
}
