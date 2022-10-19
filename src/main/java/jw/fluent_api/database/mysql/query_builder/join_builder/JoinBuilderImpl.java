package jw.fluent_api.database.mysql.query_builder.join_builder;

import jw.fluent_api.database.api.query_builder.join_builder.JoinBuilder;
import jw.fluent_api.database.mysql.query_builder.QueryBuilderImpl;
import jw.fluent_api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent_api.database.mysql.query_builder.where_builders.WhereBuilderImpl;
import jw.fluent_api.database.api.query_builder.where_builders.WhereBuilder;

public class JoinBuilderImpl extends QueryBuilderImpl implements JoinBuilder
{

    public JoinBuilderImpl(StringBuilder query) {
        super(query);
    }

    public JoinBuilder left(String table, String columnA, String columnB)
    {
        query.append(" LEFT");
        return join(table,columnA, columnB);
    }

    public JoinBuilder right(String table, String columnA, String columnB)
    {
        query.append(" RIGHT");
        return join(table,columnA, columnB);
    }

    public JoinBuilder inner(String table, String columnA, String columnB)
    {
        query.append(" INNER");
        return join(table,columnA, columnB);
    }

    @Override
    public JoinBuilder inner(String fromTable, String fromColumn, String toTable, String toColumn) {


        return inner(toTable,
                fromTable.concat(SqlSyntaxUtils.DOT).concat(fromColumn),
                toTable.concat(SqlSyntaxUtils.DOT).concat(toColumn)
                );
    }

    public JoinBuilder outer(String table, String columnA, String columnB)
    {
        query.append(" OUTER");
        return join(table,columnA, columnB);
    }

    private JoinBuilder join(String table, String columnA, String columnB)
    {
        query.append(SqlSyntaxUtils.JOIN)
                .append(table)
                .append(SqlSyntaxUtils.ON)
                .append(columnA)
                .append(SqlSyntaxUtils.EQUALS)
                .append(columnB);
        return this;
    }

    public WhereBuilder where()
    {
        return new WhereBuilderImpl(query).where();
    }


}
