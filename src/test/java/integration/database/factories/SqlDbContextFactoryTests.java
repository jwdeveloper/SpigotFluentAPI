package integration.database.factories;

import integration.database.example_data.ExampleContext;
import integration.database.example_data.UserExampleModel;
import jw.fluent_api.database.mysql.factories.SqlDbContextFactory;
import jw.fluent_api.database.mysql.models.SqlTable;
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
