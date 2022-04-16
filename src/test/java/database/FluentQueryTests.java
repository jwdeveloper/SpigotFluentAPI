package database;

import example_classes.database.UserExampleTable;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluentQueryTests extends TestBase
{

    @Test
    public void A_testSelectMany()
    {
        var mike = prepareModel("Mike");
        var john = prepareModel("John");

        sut.insert(mike);
        sut.insert(john);
        assertDoesNotThrow(() -> sut.saveChanges());
        var result = sut.select().toList();
        Assert.assertEquals(2,result.size());
    }

    @Test
    public void B_testSelectOne()
    {
        var adam = prepareModel("Adam");
        adam.setOnline(true);
        sut.insert(adam);
        assertDoesNotThrow(() -> sut.saveChanges());

        var result = sut
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

    private UserExampleTable prepareModel(String nick)
    {
        var exampleDto = new UserExampleTable();
        exampleDto.setUuid(UUID.randomUUID());
        exampleDto.setOnline(true);
        exampleDto.setNick(nick);
        exampleDto.setSurname("Tester");
        return exampleDto;
    }
}
