package integration.database.table;

import integration.database.assets.SqlTestBase;
import integration.database.example_data.OrderExampleModel;
import integration.database.example_data.UserExampleModel;
import jw.fluent_api.database.mysql.models.SqlTable;
import lombok.SneakyThrows;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlTableTests
{
    private static SqlTable<UserExampleModel> sut;
    private static SqlTable<OrderExampleModel> sut2;
    private static Connection connection;
    private static List<UserExampleModel> dataSet;

    @BeforeClass
    public static void before()
    {
        sut = new SqlTable<>(UserExampleModel.class);
        sut2= new SqlTable<>(OrderExampleModel.class);
        var operationResult  = SqlTestBase.getConnection();
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

            var ex = new OrderExampleModel();
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
                .join(UserExampleModel.class)
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

    private static List<UserExampleModel> getDataSet(int modelsAmount)
    {
        var result = new ArrayList<UserExampleModel>();
        for(int i=0;i<modelsAmount;i++)
        {
            var model = new UserExampleModel();
            model.setNick("User "+i);
            model.setUuid(UUID.randomUUID());
            result.add(model);
        }
        return result;
    }

}
