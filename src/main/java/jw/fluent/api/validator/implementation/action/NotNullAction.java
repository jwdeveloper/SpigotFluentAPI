package jw.fluent.api.validator.implementation.action;

import jw.fluent.api.validator.api.ValidationAction;
import jw.fluent.api.utilites.ActionResult;

import java.lang.reflect.Field;

public class NotNullAction implements ValidationAction
{

    @Override
    public ActionResult check(Object object, Field field) throws IllegalAccessException {
        return null;
    }
}
