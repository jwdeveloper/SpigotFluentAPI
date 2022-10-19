package integration.database.table;

import integration.database.example_data.UserExampleModel;
import jw.fluent_api.database.mysql.change_tacker.SqlChangeTracker;
import jw.fluent_api.database.api.database_table.enums.EntryState;
import jw.fluent_api.database.mysql.factories.SqlTableModelFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.UUID;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangeTrackerTests {

    private SqlChangeTracker<UserExampleModel> sut;
    private UserExampleModel exampleTestTable;

    public ChangeTrackerTests() {

        var tableModel = SqlTableModelFactory.getTableModel(UserExampleModel.class);
        sut = new SqlChangeTracker(tableModel);
        exampleTestTable = new UserExampleModel();
        exampleTestTable.setNick("Jacek");
        exampleTestTable.setOnline(false);
        exampleTestTable.setUuid(UUID.randomUUID());
        exampleTestTable.setSurname("Wacek");
        exampleTestTable.setId(0);
    }

    @Test
    public void A_shouldInsertEntity() {
        var result = sut.insert(exampleTestTable);
        Assert.assertNotNull(result);
        Assert.assertEquals(EntryState.INSERT, result.getAction());
        Assert.assertEquals(1, sut.getTrackedEntries().size());
    }


    @Test
    public void B_shouldUpdateEntity() {
        var result = sut.update(exampleTestTable);
        Assert.assertNotNull(result);
        Assert.assertEquals(EntryState.UPDATE, result.getAction());
        Assert.assertEquals(1, sut.getTrackedEntries().size());
    }

    @Test
    public void C_shouldDeleteEntity() {
        var result = sut.delete(exampleTestTable);
        Assert.assertNotNull(result);
        Assert.assertEquals(EntryState.DELETE, result.getAction());
        Assert.assertEquals(1, sut.getTrackedEntries().size());
    }

    @Test
    public void D_addManyObjects() {
        var o1 = new UserExampleModel();
        o1.setNick("Jacek");
        o1.setId(12);
        var o2 = new UserExampleModel();
        o2.setNick("Adam");
        o2.setId(321);
        var o3 = new UserExampleModel();
        o3.setNick("Mark");
        var result = sut.insert(o1);
        var result2 = sut.delete(o2);
        var result3 = sut.insert(o3);
        Assert.assertNotNull(result);
        Assert.assertEquals(EntryState.INSERT, result.getAction());
        Assert.assertEquals(3, sut.getTrackedEntries().size());
    }

}
