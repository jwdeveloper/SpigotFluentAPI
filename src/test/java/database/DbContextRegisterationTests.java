package database;

import database.assets.SqlTestBase;
import database.example_data.ExampleContext;
import jw.spigot_fluent_api.database.mysql_db.MySqlDbExtention;
import lombok.SneakyThrows;
import org.junit.Test;

public class DbContextRegisterationTests
{
    @Test
    @SneakyThrows
    public void TryCreateExampleContext()
    {
        var connection = SqlTestBase.getConnectionDto();
        var extention = new MySqlDbExtention(ExampleContext.class, connection);
        extention.pluginEnable(null);
        extention.pluginDisable(null);
    }


}
