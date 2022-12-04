package jw.fluent.api.database.api.query_builder.where_builders;

import jw.fluent.api.database.api.query_builder.group_builder.GroupBuilder;
import jw.fluent.api.database.api.query_builder.order_builder.OrderBuilderBridge;
import jw.fluent.api.database.api.query_abstract.AbstractQuery;
import jw.fluent.api.database.api.query_abstract.where.AbstractWhereQuery;

public interface WhereBuilder extends AbstractWhereQuery<WhereBuilder>, AbstractQuery, OrderBuilderBridge
{


    public GroupBuilder groupBy();
}