package jw.spigot_fluent_api.database.mysql_db;
import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlQueryBuilder;
import jw.spigot_fluent_api.database.api.DbContext;
import jw.spigot_fluent_api.database.mysql_db.database_table.SqlTable;

public class MySqlDbContext extends DbContext
{
    public SqlTable<SqlQueryBuilder> users;

    public SqlTable<SqlQueryBuilder> players;

    public SqlTable<SqlQueryBuilder> queries;

    public MySqlDbContext(String url)
    {
        super(url);
    }

    @Override
    public void onConfiguration()
    {
       // queries.add();
           users.select()
                .toList();
        saveChangesAsync();
        // iterate through the java resultset
    }

    @Override
    public void saveChangesAsync()
    {

    }
}
