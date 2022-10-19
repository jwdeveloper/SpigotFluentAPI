package jw.fluent_api.validator.implementation.action;

import jw.fluent_api.validator.api.ValidationAction;
import jw.fluent_api.utilites.ActionResult;

import java.lang.reflect.Field;

public class NotNullAction implements ValidationAction
{

    @Override
    public ActionResult check(Object object, Field field) throws IllegalAccessException {
        return null;
    }
}
