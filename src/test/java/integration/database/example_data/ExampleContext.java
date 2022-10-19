package integration.database.example_data;

import jw.spigot_fluent_api.database.api.database_table.DbTable;
import jw.spigot_fluent_api.database.mysql_db.models.SqlDbContext;

public class ExampleContext extends SqlDbContext
{
    public DbTable<UserExampleModel> users;

    public DbTable<OrderExampleModel> orders;
}