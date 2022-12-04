package jw.fluent.api.utilites.files.yml.api;

import jw.fluent.api.utilites.files.yml.api.models.YmlModel;

public interface ModelFactory<T>
{
    public YmlModel createModel(T object);
}
