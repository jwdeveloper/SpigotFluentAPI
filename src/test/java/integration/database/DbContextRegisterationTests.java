package integration.database;

import integration.database.assets.SqlTestBase;
import integration.database.example_data.ExampleContext;
import jw.fluent_api.database.mysql.MySqlDbExtention;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;

public class DbContextRegisterationTests
{
    @Test
    @SneakyThrows
    @Ignore
    public void TryCreateExampleContext()
    {
        var connection = SqlTestBase.getConnectionDto();
        var extention = new MySqlDbExtention(ExampleContext.class, connection);
        extention.pluginEnable(null);
        extention.pluginDisable(null);
    }


}
