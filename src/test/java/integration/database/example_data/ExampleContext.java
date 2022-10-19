package integration.database.example_data;

import jw.fluent_api.database.api.database_table.DbTable;
import jw.fluent_api.database.mysql.models.SqlDbContext;

public class ExampleContext extends SqlDbContext
{
    public DbTable<UserExampleModel> users;

    public DbTable<OrderExampleModel> orders;
}