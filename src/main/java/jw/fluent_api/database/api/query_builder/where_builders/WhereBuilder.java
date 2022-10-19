package jw.fluent_api.database.api.query_builder.where_builders;

import jw.fluent_api.database.api.query_abstract.AbstractQuery;
import jw.fluent_api.database.api.query_abstract.where.AbstractWhereQuery;
import jw.fluent_api.database.api.query_builder.group_builder.GroupBuilder;
import jw.fluent_api.database.api.query_builder.order_builder.OrderBuilderBridge;

public interface WhereBuilder extends AbstractWhereQuery<WhereBuilder>, AbstractQuery, OrderBuilderBridge
{


    public GroupBuilder groupBy();
}