package jw.fluent.api.files.implementation.yml.api;

import jw.fluent.api.files.implementation.yml.api.models.YmlModel;

public interface ModelFactory<T>
{
    public YmlModel createModel(T object);
}
