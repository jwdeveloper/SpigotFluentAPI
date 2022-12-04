package jw.fluent.api.database.mysql.query_builder.where_builders;

import jw.fluent.api.database.api.query_builder.group_builder.GroupBuilder;
import jw.fluent.api.database.api.query_builder.order_builder.OrderBuilder;
import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilder;
import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilderBridge;
import jw.fluent.api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent.api.database.mysql.query_builder.group_builder.GroupBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.order_builder.OrderBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.QueryBuilderImpl;

public class WhereBuilderImpl extends QueryBuilderImpl implements WhereBuilderBridge, WhereBuilder {
    public WhereBuilderImpl(StringBuilder query) {
        super(query);
    }

    public WhereBuilder where() {
        query.append(SqlSyntaxUtils.WHERE);
        return this;
    }

    @Override
    public WhereBuilder isEqual(String column, Object value) {
        final var sep = SqlSyntaxUtils.getWrapper(value);
        query.append(column).append(SqlSyntaxUtils.EQUALS).append(sep).append(value).append(sep);
        return this;
    }

    @Override
    public WhereBuilder isNotEqual(String column, Object value) {
        final var sep = SqlSyntaxUtils.getWrapper(value);
        query.append(column).append(" != ").append(sep).append(value).append(sep);
        return this;
    }

    @Override
    public WhereBuilder isGreater(String column, Number value) {
        query.append(column).append(" > ").append(value);
        return this;
    }

    @Override
    public WhereBuilder isNotGreater(String column, Number value) {
        query.append(column).append(" < ").append(value);
        return null;
    }

    @Override
    public WhereBuilder or() {
        query.append(SqlSyntaxUtils.OR);
        return this;
    }

    @Override
    public WhereBuilder and() {
        query.append(SqlSyntaxUtils.AND);
        return this;
    }

    @Override
    public WhereBuilder custom(String custom) {
        query.append(custom);
        return this;
    }

    @Override
    public OrderBuilder orderBy() {
        return new OrderBuilderImpl(query).orderBy();
    }

    @Override
    public GroupBuilder groupBy() {
        return new GroupBuilderImpl(query).groupBy();
    }


    @Override
    public WhereBuilder isIn(String column, String subQuery) {
        return prepareIn(SqlSyntaxUtils.IN,column,subQuery);
    }

    @Override
    public WhereBuilder isIn(String column, Object... values) {
        return prepareIn(SqlSyntaxUtils.IN,column,prepareValues(values));
    }

    @Override
    public WhereBuilder isNotIn(String column, String subQuery) {
        return prepareIn(SqlSyntaxUtils.NOT_IN,column,subQuery);
    }

    @Override
    public WhereBuilder isNotIn(String column, Object... values) {
        return prepareIn(SqlSyntaxUtils.NOT_IN,column,prepareValues(values));
    }

    private WhereBuilder prepareIn(String isIn, String column, String values)
    {
        query.append(column).append(isIn);
        query.append(SqlSyntaxUtils.OPEN);
        prepareValues(values);
        query.append(SqlSyntaxUtils.CLOSE);
        return this;
    }

    private String prepareValues(Object... values)
    {
        if(values == null || values.length == 0)
        {
            return SqlSyntaxUtils.EMPTY;
        }
        final var sep = SqlSyntaxUtils.getWrapper(values[0]);
        final var builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            builder.append(sep).append(values[i]).append(sep);

            if (i != values.length - 1)
                builder.append(SqlSyntaxUtils.COMMA);
        }
        return builder.toString();
    }


}
