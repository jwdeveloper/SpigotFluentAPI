package jw.fluent.api.database.mysql.query_builder.bridge_builder;

import jw.fluent.api.database.api.query_builder.bridge_builder.BridgeBuilder;
import jw.fluent.api.database.api.query_builder.group_builder.GroupBuilder;
import jw.fluent.api.database.api.query_builder.join_builder.JoinBuilder;
import jw.fluent.api.database.api.query_builder.order_builder.OrderBuilder;
import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilder;
import jw.fluent.api.database.mysql.query_builder.where_builders.WhereBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.QueryBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.group_builder.GroupBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.join_builder.JoinBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.order_builder.OrderBuilderImpl;

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
