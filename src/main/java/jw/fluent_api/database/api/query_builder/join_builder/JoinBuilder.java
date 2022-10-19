package jw.fluent_api.database.api.query_builder.join_builder;

import jw.fluent_api.database.api.query_builder.where_builders.WhereBuilderBridge;

public interface JoinBuilder extends WhereBuilderBridge
{
    public JoinBuilder left(String table, String columnA, String columnB);

    public JoinBuilder right(String table, String columnA, String columnB);

    public JoinBuilder inner(String table, String columnA, String columnB);

    public JoinBuilder inner(String fromTable, String fromColumn, String toTable, String toColumn);

    public JoinBuilder outer(String table, String columnA, String columnB);

}
