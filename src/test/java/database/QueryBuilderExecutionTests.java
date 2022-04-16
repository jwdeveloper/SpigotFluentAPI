package database;

import example_classes.database.UserExampleTable;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnection;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableQueryFactory;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnectionDto;
import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlQueryBuilder;
import lombok.SneakyThrows;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryBuilderExecutionTests
{

    private Optional<Connection> connectionOptional;

    @Before
    public void Prepared()
    {
       var mySqlConnectionDto = new SqlConnectionDto();
        mySqlConnectionDto.setServer("sql.pukawka.pl:3306");
        mySqlConnectionDto.setDatabase("857511_cybercity_test");
        mySqlConnectionDto.setUser("857511");
        mySqlConnectionDto.setPassword("0WuwJ0MjgYVL2mq");

        SqlConnection connector = new SqlConnection();
        connectionOptional = connector.getConnection(mySqlConnectionDto);
    }

    @After
    public void OnEndTest()
    {
        if(connectionOptional.isEmpty())
        {
            return;
        }
        try {
            connectionOptional.get().close();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    @SneakyThrows
    @Test
    public void A_CheckConnection()
    {
        Assert.assertTrue(connectionOptional.isPresent());
    }



    @Test
    public void B_TryCreateTable() throws SQLException {
        if(connectionOptional.isEmpty())
        {
           return;
        }
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var createTable = TableQueryFactory.createTable(UserExampleTable.class);
        System.out.println(createTable);
        assertDoesNotThrow(() -> statement.execute(createTable));
    }

    @Test
    public void C_TryInsertRecords() throws SQLException {
        if(connectionOptional.isEmpty())
        {
            return;
        }
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var insertQuery = SqlQueryBuilder
                .insert()
                .table(UserExampleTable.class)
                .columns("nick","uuid","surname")
                .values("Jacek", UUID.randomUUID(),"Testowy")
                .values("Adam", UUID.randomUUID(),"Roboczy")
                .getQuery();
        System.out.println(insertQuery);
        assertDoesNotThrow(() -> statement.execute(insertQuery));
    }

    @Test
    public void D_TryUpdateRecords() throws SQLException {
        if(connectionOptional.isEmpty())
        {
            return;
        }
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var updateQuery = SqlQueryBuilder
                .update()
                .table(UserExampleTable.class)
                .set("surname","Nowe")
                .where()
                .isEqual("nick","Jacek")
                .getQuery();
        System.out.println(updateQuery);
        assertDoesNotThrow(() -> statement.execute(updateQuery));
    }

    @Test
    public void E_TrySelectRecords() throws SQLException {
        if(connectionOptional.isEmpty())
        {
            return;
        }
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var selectQuery = SqlQueryBuilder
                .select()
                .columns("*")
                .from(UserExampleTable.class)
                .getQuery();
        System.out.println(selectQuery);
        assertDoesNotThrow(() -> statement.execute(selectQuery));
    }

    @Test
    public void F_TryDeleteRecords() throws SQLException {
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var deleteQuery = SqlQueryBuilder
                .delete()
                .from(UserExampleTable.class)
                .getQuery();
        System.out.println(deleteQuery);
        assertDoesNotThrow(() -> statement.execute(deleteQuery));
    }

    @Test
    public void G_TryDeleteTable() throws SQLException {
        if(connectionOptional.isEmpty())
        {
            return;
        }
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var dropTableQuery = TableQueryFactory.dropTable(UserExampleTable.class);
        System.out.println(dropTableQuery);
        assertDoesNotThrow(() -> statement.execute(dropTableQuery));
    }
}
