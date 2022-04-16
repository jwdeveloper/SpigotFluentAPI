package database;

import example_classes.database.OrderExampleTable;
import example_classes.database.UserExampleTable;
import jw.spigot_fluent_api.database.api.database_table.models.ColumnModel;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableQueryFactory;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableModelFactory;
import org.junit.Assert;
import org.junit.Test;


public class TableBuilderTests
{
    @Test
    public void TableModelCreatorTest()
    {
        var creator = new TableModelFactory();
        var model = creator.getTableModel(UserExampleTable.class);


        Assert.assertEquals(model.getName(), UserExampleTable.class.getSimpleName());
        Assert.assertEquals(model.getClazz(), UserExampleTable.class);
        Assert.assertEquals(model.getColumnList().size(),5);

        var result = TableQueryFactory.createTable(UserExampleTable.class);
        System.out.println(result);
        Assert.assertNotEquals(result,"");
    }

    @Test
    public void CreateTableWithFK()
    {
        var creator = new TableModelFactory();
        var model = creator.getTableModel(OrderExampleTable.class);

        var columns =model.getColumnList()
                .stream()
                .filter(ColumnModel::isForeignKey)
                .findFirst();
        Assert.assertTrue(columns.isPresent());
        var fk = columns.get();

        Assert.assertEquals(fk.getForeignKeyName(),"userId");
        Assert.assertEquals(fk.getForeignKeyReference(),"id");
        var result = TableQueryFactory.createTable(OrderExampleTable.class);
        System.out.println(result);
    }
}
