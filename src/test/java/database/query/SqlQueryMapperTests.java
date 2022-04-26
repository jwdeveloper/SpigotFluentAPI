package database.query;

import database.assets.SqlTestBase;
import database.example_data.OrderExampleModel;
import database.example_data.UserExampleModel;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.UUID;

public class SqlQueryMapperTests extends SqlTestBase
{

    @Test
    public void ShouldGetJoinedData() throws SQLException
    {
        var userModel = new UserExampleModel();
        userModel.setNick("Jacek");
        userModel.setOnline(false);
        userModel.setUuid(UUID.randomUUID());

        userTable.insert(userModel);
        userTable.saveChanges();


        var orderModel = new OrderExampleModel();
        orderModel.setProductName("Product");
        orderModel.setUserId(1);

        orderTable.insert(orderModel);
        orderTable.saveChanges();

        var orderOptional = orderTable
                .select()
                .join(UserExampleModel.class)
                .first();

        Assert.assertTrue(orderOptional.isPresent());
        var order = orderOptional.get();
        Assert.assertNotNull(order.getUser());
        Assert.assertEquals(userModel.getNick(), order.getUser().getNick());
    }

}
