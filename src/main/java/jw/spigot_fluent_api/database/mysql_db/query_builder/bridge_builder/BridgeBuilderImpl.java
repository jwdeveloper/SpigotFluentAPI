package jw.spigot_fluent_api.database.mysql_db.query_builder.bridge_builder;

import jw.spigot_fluent_api.database.api.query_builder.bridge_builder.BridgeBuilder;
import jw.spigot_fluent_api.database.api.query_builder.group_builder.GroupBuilder;
import jw.spigot_fluent_api.database.api.query_builder.join_builder.JoinBuilder;
import jw.spigot_fluent_api.database.mysql_db.query_builder.QueryBuilderImpl;
import jw.spigot_fluent_api.database.mysql_db.query_builder.group_builder.GroupBuilderImpl;
import jw.spigot_fluent_api.database.mysql_db.query_builder.join_builder.JoinBuilderImpl;
import jw.spigot_fluent_api.database.mysql_db.query_builder.order_builder.OrderBuilderImpl;
import jw.spigot_fluent_api.database.api.query_builder.order_builder.OrderBuilder;
import jw.spigot_fluent_api.database.mysql_db.query_builder.where_builders.WhereBuilderImpl;
import jw.spigot_fluent_api.database.api.query_builder.where_builders.WhereBuilder;

public class BridgeBuilderImpl extends QueryBuilderImpl implements BridgeBuilder
{
    public BridgeBuilderImpl(StringBuilder query) {
        super(query);
    }

    public JoinBuilder join()
    {
        return new JoinBuilderImpl(query);
    }

    public WhereBuilder where()
    {
        return new WhereBuilderImpl(query).where();
    }

    public GroupBuilder groupBy()
    {
        return new GroupBuilderImpl(query).groupBy();
    }

    public OrderBuilder orderBy()
    {
        return new OrderBuilderImpl(query).orderBy();
    }
}
