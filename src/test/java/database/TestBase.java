package database;

import example_classes.database.UserExampleTable;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnection;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnectionDto;
import jw.spigot_fluent_api.database.mysql_db.database_table.SqlTable;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableQueryFactory;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import lombok.SneakyThrows;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.util.Optional;

public class TestBase
{
    protected static SqlTable<UserExampleTable> sut;
    protected static Connection connection;

    @BeforeClass
    public static void before()
    {
        sut = new SqlTable<>(UserExampleTable.class);
        var operationResult  = getConnection();
        Assert.assertTrue(operationResult.isPresent());
        connection = operationResult.get();
        sut.setConnection(connection);
        sut.dropTable();
        sut.createTable();
        FluentLogger.success("Init database");
    }


    @AfterClass
    @SneakyThrows
    public static void after()
    {
        connection.close();
        FluentLogger.success("Database closed");
    }

    private static Optional<Connection> getConnection()
    {
        var mySqlConnectionDto = new SqlConnectionDto();
        mySqlConnectionDto.setServer("sql.pukawka.pl:3306");
        mySqlConnectionDto.setDatabase("857511_cybercity_test");
        mySqlConnectionDto.setUser("857511");
        mySqlConnectionDto.setPassword("0WuwJ0MjgYVL2mq");

        SqlConnection connector = new SqlConnection();
        return connector.getConnection(mySqlConnectionDto);
    }
}
