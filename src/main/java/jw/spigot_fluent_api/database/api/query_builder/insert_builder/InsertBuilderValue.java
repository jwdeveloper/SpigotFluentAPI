package jw.spigot_fluent_api.database.api.query_builder.insert_builder;

import jw.spigot_fluent_api.database.api.query_abstract.AbstractQuery;

public interface InsertBuilderValue extends AbstractQuery
{
    public InsertBuilderValue values(Object... values);
}
