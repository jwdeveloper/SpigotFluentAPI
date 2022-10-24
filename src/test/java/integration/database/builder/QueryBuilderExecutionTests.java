package integration.database.builder;

import integration.database.assets.SqlTestBase;
import integration.database.example_data.UserExampleModel;
import jw.fluent_api.database.mysql.factories.SqlTableQueryFactory;
import jw.fluent_api.database.mysql.query_builder.SqlQueryBuilder;
import lombok.SneakyThrows;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class QueryBuilderExecutionTests
{

    private Optional<Connection> connectionOptional;

    @Before
    public void Prepared()
    {
        connectionOptional = SqlTestBase.getConnection();
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
        var createTable = SqlTableQueryFactory.createTable(UserExampleModel.class);
        System.out.println(createTable);
        Assertions.assertDoesNotThrow(() -> statement.execute(createTable));
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
                .table("Users")
                .columns("nick","uuid","surname")
                .values("Jacek", UUID.randomUUID(),"Testowy")
                .values("Adam", UUID.randomUUID(),"Roboczy")
                .getQuery();
        System.out.println(insertQuery);
        Assertions.assertDoesNotThrow(() -> statement.execute(insertQuery));
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
                .table("Users")
                .set("surname","Nowe")
                .where()
                .isEqual("nick","Jacek")
                .getQuery();
        System.out.println(updateQuery);
        Assertions.assertDoesNotThrow(() -> statement.execute(updateQuery));
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
                .from("Users")
                .getQuery();
        System.out.println(selectQuery);
        Assertions.assertDoesNotThrow(() -> statement.execute(selectQuery));
    }

    @Test
    public void F_TryDeleteRecords() throws SQLException {
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var deleteQuery = SqlQueryBuilder
                .delete()
                .from("Users")
                .getQuery();
        System.out.println(deleteQuery);
        Assertions.assertDoesNotThrow(() -> statement.execute(deleteQuery));
    }

    @Test
    public void G_TryDeleteTable() throws SQLException {
        if(connectionOptional.isEmpty())
        {
            return;
        }
        var connection = connectionOptional.get();
        var statement = connection.createStatement();
        var dropTableQuery = SqlTableQueryFactory.dropTable(UserExampleModel.class);
        System.out.println(dropTableQuery);
        Assertions.assertDoesNotThrow(() -> statement.execute(dropTableQuery));
    }
}
