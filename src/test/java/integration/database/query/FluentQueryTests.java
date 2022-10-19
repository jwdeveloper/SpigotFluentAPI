package integration.database.query;

import integration.database.assets.SqlTestBase;
import integration.database.example_data.UserExampleModel;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluentQueryTests extends SqlTestBase
{

    @Test
    public void A_testSelectMany()
    {
        var mike = prepareModel("Mike");
        var john = prepareModel("John");

        userTable.insert(mike);
        userTable.insert(john);
        assertDoesNotThrow(() -> userTable.saveChanges());
        var result = userTable.select().toList();
        Assert.assertEquals(2,result.size());
    }

    @Test
    public void B_testSelectOne()
    {
        var adam = prepareModel("Adam");
        adam.setOnline(true);
        userTable.insert(adam);
        assertDoesNotThrow(() -> userTable.saveChanges());

        var result = userTable
                .select()
                .columns("nick","id")
                .where()
                .isEqual("nick",adam.getNick())
                .and()
                .isEqual("online",true)
                .first();

        Assert.assertTrue(result.isPresent());
        var entity = result.get();
        Assert.assertEquals(adam.getNick(), entity.getNick());
        Assert.assertEquals(adam.getId(), entity.getId());
    }

    private UserExampleModel prepareModel(String nick)
    {
        var exampleDto = new UserExampleModel();
        exampleDto.setUuid(UUID.randomUUID());
        exampleDto.setOnline(true);
        exampleDto.setNick(nick);
        exampleDto.setSurname("Tester");
        return exampleDto;
    }
}
