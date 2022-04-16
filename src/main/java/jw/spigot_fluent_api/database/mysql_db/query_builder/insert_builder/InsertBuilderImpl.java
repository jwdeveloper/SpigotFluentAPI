package jw.spigot_fluent_api.database.mysql_db.query_builder.insert_builder;

import jw.spigot_fluent_api.database.api.query_builder.insert_builder.InsertBuilder;
import jw.spigot_fluent_api.database.api.query_builder.insert_builder.InsertBuilderColumn;
import jw.spigot_fluent_api.database.api.query_builder.insert_builder.InsertBuilderValue;
import jw.spigot_fluent_api.database.mysql_db.query_builder.QueryBuilderImpl;
import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlSyntaxUtils;

public class InsertBuilderImpl extends QueryBuilderImpl implements InsertBuilder,InsertBuilderColumn, InsertBuilderValue
{
   private boolean isFirstValueAdded = false;

    public InsertBuilderImpl() {
        this(new StringBuilder());
    }

    public InsertBuilderImpl(StringBuilder query) {
        super(query);
        query.append(SqlSyntaxUtils.INSERT_INTO);
    }

    public InsertBuilderColumn table(Class<?> tableClass)
    {
        return table(tableClass.getSimpleName());
    }
    public InsertBuilderColumn table(String table)
    {
        query.append(table);
        return this;
    }

    public InsertBuilderValue columns(String... columns)
    {
        query.append(SqlSyntaxUtils.OPEN);
        for(var i =0;i<columns.length;i++)
        {
            query.append(columns[i]);
            if(i != columns.length-1)
            {
                query.append(SqlSyntaxUtils.COMMA);
            }
        }
        query.append(SqlSyntaxUtils.CLOSE);
        query.append(SqlSyntaxUtils.VALUES);
        return this;
    }



    public InsertBuilderValue values(Object... values)
    {
        if(isFirstValueAdded)
        {
            query.append(SqlSyntaxUtils.COMMA);
        }
        isFirstValueAdded = true;

        query.append(SqlSyntaxUtils.OPEN);
        for(var i =0;i<values.length;i++)
        {
            var wrapper = SqlSyntaxUtils.getWrapper(values[i]);
            var value = values[i]==null? SqlSyntaxUtils.NULL:values[i];
            query.append(wrapper).append(value).append(wrapper);
            if(i != values.length-1)
            {
                query.append(SqlSyntaxUtils.COMMA);
            }
        }
        query.append(SqlSyntaxUtils.CLOSE);

        return this;
    }
}
