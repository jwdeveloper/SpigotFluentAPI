package jw.spigot_fluent_api.utilites.files.json.execution;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


public class BindingFieldSkip implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass)
    {
      //  return  BindingField.class.getName().equalsIgnoreCase(aClass.getName());
        return false;
    }
}
