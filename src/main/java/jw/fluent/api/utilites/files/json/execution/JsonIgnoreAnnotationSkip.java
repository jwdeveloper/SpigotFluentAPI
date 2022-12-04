package jw.fluent.api.utilites.files.json.execution;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import jw.fluent.api.utilites.files.json.annotations.JsonIgnore;


public class JsonIgnoreAnnotationSkip implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(JsonIgnore.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}