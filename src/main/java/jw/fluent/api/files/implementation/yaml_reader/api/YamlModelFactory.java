package jw.fluent.api.files.implementation.yaml_reader.api;

import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlModel;

public interface YamlModelFactory
{
    public <T> YamlModel createModel(Class<T> clazz) throws ClassNotFoundException;
}
