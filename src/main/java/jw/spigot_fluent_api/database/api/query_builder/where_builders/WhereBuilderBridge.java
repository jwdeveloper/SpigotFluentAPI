package jw.spigot_fluent_api.database.api.query_builder.where_builders;

import jw.spigot_fluent_api.database.api.query_abstract.AbstractQuery;

public interface WhereBuilderBridge extends AbstractQuery
{
    WhereBuilder where();
}