package integration.database.example_data;

import jw.fluent.api.database.api.database_table.DbTable;
import jw.fluent.api.database.mysql.models.SqlDbContext;

public class ExampleContext extends SqlDbContext
{
    public DbTable<UserExampleModel> users;

    public DbTable<OrderExampleModel> orders;
}