package jw.spigot_fluent_api.database.mysql_db.query_builder;

import jw.spigot_fluent_api.database.api.query_abstract.AbstractQuery;

public abstract class QueryBuilderImpl implements AbstractQuery
{
    protected final StringBuilder query;

    public QueryBuilderImpl(StringBuilder query)
   {
       this.query = query;
   }

    public String getQueryClosed()
    {
        return query.append(SqlSyntaxUtils.SEMI_COL).toString();
    }

    public String getQuery()
    {
        return query.toString();
    }
}
