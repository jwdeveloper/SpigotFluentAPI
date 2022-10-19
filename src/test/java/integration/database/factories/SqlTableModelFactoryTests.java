package integration.database.factories;

import integration.database.example_data.OrderExampleModel;
import integration.database.example_data.UserExampleModel;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlTableModelFactory;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlTableQueryFactory;
import org.junit.Assert;
import org.junit.Test;


public class SqlTableModelFactoryTests
{
    @Test
    public void TableModelCreatorTest()
    {
        var model = SqlTableModelFactory.getTableModel(UserExampleModel.class);


        Assert.assertEquals(model.getName(),"Users");
        Assert.assertEquals(model.getClazz(), UserExampleModel.class);
        Assert.assertEquals(model.getColumnList().size(),5);

        var result = SqlTableQueryFactory.createTable(UserExampleModel.class);
        System.out.println(result);
        Assert.assertNotEquals(result,"");
    }

    @Test
    public void CreateTableWithFK()
    {
        var model = SqlTableModelFactory.getTableModel(OrderExampleModel.class);

        var columnRefs = model.getForeignKeys();
        Assert.assertEquals(1,columnRefs.size());
        var columnRef = columnRefs.get(0);

        Assert.assertEquals(columnRef.getForeignKeyTableName(),"Users");
        Assert.assertEquals(columnRef.getForeignKeyName(),"userId");
        Assert.assertEquals(columnRef.getForeignKeyReference(),"id");
        var result = SqlTableQueryFactory.createTable(OrderExampleModel.class);
        System.out.println(result);
    }
}
