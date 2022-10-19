package jw.fluent_api.utilites.files.yml.api;

import jw.fluent_api.utilites.files.yml.api.models.YmlModel;

public interface ModelFactory<T>
{
    public YmlModel createModel(T object);
}
