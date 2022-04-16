package jw.spigot_fluent_api.database.api.query_builder.bridge_builder;

import jw.spigot_fluent_api.database.api.query_abstract.AbstractQuery;
import jw.spigot_fluent_api.database.api.query_builder.group_builder.GroupBuilder;
import jw.spigot_fluent_api.database.api.query_builder.join_builder.JoinBuilder;
import jw.spigot_fluent_api.database.api.query_builder.order_builder.OrderBuilder;
import jw.spigot_fluent_api.database.api.query_builder.where_builders.WhereBuilderBridge;
import jw.spigot_fluent_api.database.api.query_builder.where_builders.WhereBuilder;

public interface BridgeBuilder extends WhereBuilderBridge, AbstractQuery
{

    public JoinBuilder join();

    public WhereBuilder where();

    public GroupBuilder groupBy();

    public OrderBuilder orderBy();

}