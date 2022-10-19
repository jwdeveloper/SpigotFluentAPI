package jw.fluent_api.database.mysql.query_builder.table_builder;

import jw.fluent_api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent_api.database.mysql.utils.SqlTypes;

public class ColumnBuilder
{
    private final TableBuilder tableBuilder;
    private final StringBuilder stringBuilder;
    public ColumnBuilder(TableBuilder tableBuilder, String name)
    {
        this.tableBuilder = tableBuilder;
        stringBuilder = new StringBuilder();
        stringBuilder.append(name);
    }


    public ColumnBuilder setPrimaryKey()
    {
        stringBuilder.append(SqlSyntaxUtils.PRIMARY_KEY);
        return this;
    }

    public ColumnBuilder setAutoIncrement()
    {
        stringBuilder.append(SqlSyntaxUtils.AUTO_INCREMENT);
        return this;
    }

    public ColumnBuilder setRequired()
    {
        stringBuilder.append(SqlSyntaxUtils.NOT_NULL);
        return this;
    }

    public ColumnBuilder setType(String type)
    {
        stringBuilder.append(SqlSyntaxUtils.SPACE).append(type).append(SqlSyntaxUtils.SPACE);
        return this;
    }

    public ColumnBuilder setType(String type, int size)
    {
        if(type.equals(SqlTypes.BOOL))
        {
            return setType(type);
        }
        type+= SqlSyntaxUtils.OPEN +size+ SqlSyntaxUtils.CLOSE;
        return setType(type);
    }

    public TableBuilder addColumn()
    {
        tableBuilder.columns.add(stringBuilder.toString());
        return tableBuilder;
    }
}
