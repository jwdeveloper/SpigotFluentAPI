package jw.fluent_api.database.mysql.query_builder;

import jw.fluent_api.database.api.query_builder.delete_builder.DeleteBuilder;
import jw.fluent_api.database.api.query_builder.insert_builder.InsertBuilder;
import jw.fluent_api.database.api.query_builder.select_builder.SelectBuilder;
import jw.fluent_api.database.api.query_builder.update_builder.UpdateBuilder;
import jw.fluent_api.database.mysql.query_builder.delete_builder.DeleteBuilderImpl;
import jw.fluent_api.database.mysql.query_builder.insert_builder.InsertBuilderImpl;
import jw.fluent_api.database.mysql.query_builder.select_builder.SelectBuilderImpl;
import jw.fluent_api.database.mysql.query_builder.update_builder.UpdateBuilderImpl;
import jw.fluent_api.database.mysql.query_builder.table_builder.TableBuilder;

public final class SqlQueryBuilder
{
    public static SelectBuilder select()
    {
        return new SelectBuilderImpl();
    }

    public static InsertBuilder insert()
    {
        return new InsertBuilderImpl();
    }

    public static DeleteBuilder delete()
    {
        return new DeleteBuilderImpl();
    }

    public static UpdateBuilder update()
    {
        return new UpdateBuilderImpl();
    }

    public static TableBuilder table() {return new TableBuilder(); }
}
