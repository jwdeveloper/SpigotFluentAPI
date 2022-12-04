package jw.fluent.api.database.mysql.query_fluent;

import jw.fluent.api.database.api.database_table.models.TableModel;
import jw.fluent.api.database.api.query_fluent.order.OrderFluent;
import jw.fluent.api.database.api.query_fluent.where.WhereFluent;
import jw.fluent.api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent.api.database.mysql.query_builder.where_builders.WhereBuilderImpl;

import java.sql.Connection;

public class SqlWhere<T> extends SqlQuery<T> implements WhereFluent<T> {

    private final WhereBuilderImpl whereBuilder;

    public SqlWhere(StringBuilder query, Connection connection, TableModel tableModel)
    {
        super(connection, tableModel);
        whereBuilder = new WhereBuilderImpl(query);
        whereBuilder.where();
    }

    public SqlWhere<T> isEqual(String column, Object value) {
        whereBuilder.isEqual(column,value);
        return this;
    }


    public SqlWhere<T> isNotEqual(String column, Object value) {
        whereBuilder.isNotEqual(column,value);
        return this;
    }


    public SqlWhere<T> isGreater(String column, Number value) {
        whereBuilder.isGreater(column,value);
        return this;
    }


    public SqlWhere<T> isNotGreater(String column, Number value) {
        whereBuilder.isNotGreater(column,value);
        return this;
    }


    public SqlWhere<T> isIn(String column, String subQuery) {
        whereBuilder.isIn(column,subQuery);
        return this;
    }


    public SqlWhere<T> isIn(String column, Object... values) {
        whereBuilder.isIn(column,values);
        return this;
    }


    public SqlWhere<T> isNotIn(String column, String subQuery) {
        whereBuilder.isNotIn(column,subQuery);
        return this;
    }


    public SqlWhere<T> isNotIn(String column, Object... values) {
        whereBuilder.isNotIn(column,values);
        return this;
    }


    public SqlWhere<T> or() {
        whereBuilder.or();
        return this;
    }


    public SqlWhere<T> and() {
        whereBuilder.and();
        return this;
    }


    public SqlWhere<T> custom(String custom) {
        whereBuilder.custom(custom);
        return this;
    }


    public String getQueryClosed() {
        return getQuery().concat(SqlSyntaxUtils.SEMI_COL);
    }

    @Override
    public String getQuery() {
        return whereBuilder.getQuery();
    }

    @Override
    public OrderFluent<T> orderBy() {
        return new SqlOrder<T>(query,connection,tableModel);
    }
}
