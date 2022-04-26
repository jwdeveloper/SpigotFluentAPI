package database.factories;

import database.example_data.ExampleContext;
import database.example_data.UserExampleModel;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlDbContextFactory;
import jw.spigot_fluent_api.database.mysql_db.models.SqlTable;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SqlDbContextFactoryTests
{
    @Test
    public void shouldCreateDbContext()
    {
       var context = SqlDbContextFactory.getDbContext(ExampleContext.class);
        Assertions.assertNotNull(context);
        Assertions.assertNotNull(context.users);
        Assertions.assertNotNull(context.orders);

        var table = (SqlTable<UserExampleModel>)context.users;

        
        Assertions.assertNotNull(table);

    }
}
