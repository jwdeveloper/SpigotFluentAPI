package database;

import example_classes.database.OrderExampleTable;
import example_classes.database.UserExampleTable;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnection;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnectionDto;
import jw.spigot_fluent_api.database.mysql_db.database_table.SqlTable;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableQueryFactory;
import lombok.SneakyThrows;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlTableTests
{
    private static SqlTable<UserExampleTable> sut;
    private static SqlTable<OrderExampleTable> sut2;
    private static Connection connection;
    private static List<UserExampleTable> dataSet;

    @BeforeClass
    public static void before()
    {
        sut = new SqlTable<>(UserExampleTable.class);
        sut2= new SqlTable<>(OrderExampleTable.class);
        var operationResult  = getConnection();
        Assert.assertTrue(operationResult.isPresent());
        connection = operationResult.get();
        sut.setConnection(connection);
        sut2.setConnection(connection);

        sut.dropTable();
        sut.createTable();

        sut2.dropTable();
        sut2.createTable();
        dataSet = getDataSet(10);
    }

    //10 -> 250ms
    //100 -> 2sec 200
    @Test
    public void A_insertModelsTest()
    {
            for (var data : dataSet) {
                sut.insert(data);
            }

            Assert.assertEquals(dataSet.size(), sut.getChangeTracker().getTrackedEntries().size());
            assertDoesNotThrow(() -> sut.saveChanges());

            var ex = new OrderExampleTable();
            ex.setUserId(1);
            ex.setProductName("Product");
            sut2.insert(ex);
            assertDoesNotThrow(() -> sut2.saveChanges());
    }
    //10 -> 270ms
    //100 -> 2sec 150
    @Test
    public void B_updateModelsTest()
    {
            for (var data : dataSet) {
                data.setSurname("Updated");
                data.setOnline(true);
                data.setUuid(null);
                sut.update(data);
            }
            Assert.assertEquals(dataSet.size(), sut.getChangeTracker().getTrackedEntries().size());
            assertDoesNotThrow(() -> sut.saveChanges());
    }


   //10 -> 82 ms
    @Test
    public void C_selectModelsTest()
    {
        var result = sut.select().toList();
        Assert.assertEquals(dataSet.size(), result.size());
        var entity = result.get(0);
        Assert.assertTrue(entity.isOnline());
        Assert.assertEquals(entity.getSurname(), "Updated");
        Assert.assertNull(entity.getUuid());
    }

    //10 -> 82 ms
    @Test
    public void D_selectWithInclude()
    {
        var result = sut2
                .select()
                .join(UserExampleTable.class)
                .toList();

        Assert.assertEquals(1,result.size());
        var entity = result.get(0);
        Assert.assertEquals(entity.getId(),1);
        Assert.assertEquals(entity.getProductName(), "Product");
        Assert.assertEquals(entity.getUserId(),1);
        Assert.assertNotNull(entity.getUser());
    }

    //10 -> 270ms
    //100 ->  2sec 78
    @Test
    public void E_deleteModelsTest()
    {
            for (var data: dataSet)
            {
                sut.delete(data);
            }
            Assert.assertEquals(dataSet.size(),sut.getChangeTracker().getTrackedEntries().size());
            assertDoesNotThrow(() -> sut.saveChanges());
    }



    @SneakyThrows
    @AfterClass
    public static void after()
    {
        connection.close();
    }

    private static List<UserExampleTable> getDataSet(int modelsAmount)
    {
        var result = new ArrayList<UserExampleTable>();
        for(int i=0;i<modelsAmount;i++)
        {
            var model = new UserExampleTable();
            model.setNick("User "+i);
            model.setUuid(UUID.randomUUID());
            result.add(model);
        }
        return result;
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
