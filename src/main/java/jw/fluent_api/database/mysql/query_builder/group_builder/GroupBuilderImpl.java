package jw.fluent_api.database.mysql.query_builder.group_builder;

import jw.fluent_api.database.api.query_builder.group_builder.GroupBuilder;
import jw.fluent_api.database.api.query_builder.where_builders.WhereBuilder;
import jw.fluent_api.database.mysql.query_builder.QueryBuilderImpl;
import jw.fluent_api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent_api.database.mysql.query_builder.order_builder.OrderBuilderImpl;
import jw.fluent_api.database.api.query_builder.order_builder.OrderBuilder;
import jw.fluent_api.database.mysql.query_builder.where_builders.WhereBuilderImpl;

public class GroupBuilderImpl extends QueryBuilderImpl implements GroupBuilder
{
    public GroupBuilderImpl(StringBuilder query) {
        super(query);
    }

    public GroupBuilder groupBy()
    {
        query.append(SqlSyntaxUtils.GROUP_BY);
        return this;
    }


    public GroupBuilder table(String table) {
        query.append(table);
        return this;
    }

    public WhereBuilder having() {
        return new WhereBuilderImpl(query).custom(SqlSyntaxUtils.HAVING);
    }

    public OrderBuilder orderBy() {
        return new OrderBuilderImpl(query).orderBy();
    }
}
