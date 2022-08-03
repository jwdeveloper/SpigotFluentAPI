package jw.spigot_fluent_api.database.api.query_builder.where_builders;

import jw.spigot_fluent_api.database.api.query_abstract.AbstractQuery;
import jw.spigot_fluent_api.database.api.query_abstract.where.AbstractWhereQuery;
import jw.spigot_fluent_api.database.api.query_builder.group_builder.GroupBuilder;
import jw.spigot_fluent_api.database.api.query_builder.order_builder.OrderBuilderBridge;

public interface WhereBuilder extends AbstractWhereQuery<WhereBuilder>, AbstractQuery, OrderBuilderBridge
{


    public GroupBuilder groupBy();
}