package desing_patterns.dependecy_injection;

import assets.*;
import assets.annotations.TestingAnnotation;
import assets.interfaces.TestingInterface;
import desing_patterns.dependecy_injection.assets.ExampleDefaultObject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ContainerTests {

    private Container container;

    @Before
    public void init() {
        container = new Container();
        container.register(SomeRepo.class, LifeTime.SINGLETON);
        container.register(SuperInventory.class, LifeTime.SINGLETON);
        container.register(PlayerStats.class, LifeTime.SINGLETON);
    }


    @Test
    public void should_register_default_object()
    {
        var text = "Example";
        var object = new ExampleDefaultObject(text);
        container.register(object);

        var result = container.get(ExampleDefaultObject.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(object,result);
        Assertions.assertEquals(text,result.getName());
    }

    @Test
    public void should_classes_be_registered() {

        var superInventory = container.get(SuperInventory.class);
        var repo = container.get(SomeRepo.class);
        var playerStats = container.get(PlayerStats.class);

        Assertions.assertNotNull(repo);
        Assertions.assertNotNull(superInventory);
        Assertions.assertNotNull(playerStats);

        Assertions.assertEquals(superInventory.getPlayerStats(),playerStats);
        Assertions.assertEquals(superInventory.getSomeRepo(),repo);
    }
    @Test()
    public void should_get_by_annotation()
    {
        var objects = container.getAllByAnnotation(TestingAnnotation.class);
        Assertions.assertEquals(1,objects.size());
    }

    @Test()
    public void should_get_by_interface()
    {
        var objects = container.getAllByInterface(TestingInterface.class);
        Assertions.assertEquals(1,objects.size());
    }

    @Test()
    public void should_get_by_parentClass()
    {
        var objects = container.getAllByParentType(BaseClassTesting.class);
        Assertions.assertEquals(1,objects.size());
    }

    @Test(expected = StackOverflowError.class)
    public void should_throw_stackOverflow_exception()
    {
        container.register(WrongUsedDependecyInjection.class, LifeTime.TRANSIENT);
    }

}
