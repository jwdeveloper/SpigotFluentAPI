package jw.spigot_fluent_api.database.api.query_fluent.select;

import jw.spigot_fluent_api.database.api.query_fluent.QueryFluent;

public interface SelectFluentBridge<T> extends QueryFluent<T>, SelectFluent<T>
{
    public SelectFluent<T> columns(String ... columns);
}