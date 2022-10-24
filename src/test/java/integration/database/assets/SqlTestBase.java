package integration.database.assets;

import integration.database.example_data.OrderExampleModel;
import integration.database.example_data.UserExampleModel;
import jw.fluent_api.database.mysql.factories.SqlConnectionFactory;
import jw.fluent_api.database.mysql.models.SqlConnection;
import jw.fluent_api.database.mysql.models.SqlTable;
import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.files.json.JsonUtility;
import lombok.SneakyThrows;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.util.Optional;

public class SqlTestBase
{
    protected static SqlTable<UserExampleModel> userTable;
    protected static SqlTable<OrderExampleModel> orderTable;
    protected static Connection connection;

    @BeforeClass
    public static void before()
    {
        userTable = new SqlTable<>(UserExampleModel.class);
        orderTable = new SqlTable<>(OrderExampleModel.class);
        var operationResult  = getConnection();
        Assert.assertTrue(operationResult.isPresent());
        connection = operationResult.get();
        userTable.setConnection(connection);
        userTable.dropTable();
        userTable.createTable();

        orderTable.setConnection(connection);
        orderTable.dropTable();
        orderTable.createTable();
        OldLogger.success("Database created");
    }


    @AfterClass
    @SneakyThrows
    public static void after()
    {
        connection.close();
        OldLogger.success("Database closed");
    }

    public static Optional<Connection> getConnection()
    {
        var connector = new SqlConnectionFactory();
        return connector.getConnection(getConnectionDto());
    }

    public static SqlConnection getConnectionDto()
    {
        var path = FileUtility.serverPath()+"\\src\\test\\java\\database\\assets";
        return JsonUtility.load(path,"TestConnection",SqlConnection.class);
    }
}
