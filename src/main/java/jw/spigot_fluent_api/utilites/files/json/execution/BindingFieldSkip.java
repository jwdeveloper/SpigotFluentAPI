package jw.spigot_fluent_api.utilites.files.json.execution;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.binding.Observable;


public class BindingFieldSkip implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes)
    {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass)
    {
        if(aClass.equals(Observable.class))
        {
            return true;
        }
        return false;
    }
}
