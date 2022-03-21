package jw.spigot_fluent_api.fluent_validator.implementation.action;

import jw.spigot_fluent_api.fluent_validator.api.ValidationAction;
import jw.spigot_fluent_api.utilites.ActionResult;

import java.lang.reflect.Field;

public class NotNullAction implements ValidationAction
{

    @Override
    public ActionResult check(Object object, Field field) throws IllegalAccessException {
        return null;
    }
}
