package unit.desing_patterns.dependecy_injection;


import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import org.junit.Ignore;
import unit.assets.annotations.TestingAnnotation;
import unit.assets.interfaces.TestingInterface;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import unit.assets.*;

public class ContainerTests {

    private FluentContainer container;
    @Before
    public void init() throws Exception {

        var builder = new ContainerBuilderImpl();
        builder.register(SomeRepo.class, LifeTime.SINGLETON);
        builder.register(SuperInventory.class, LifeTime.SINGLETON);
        builder.register(PlayerStats.class, LifeTime.SINGLETON);
       // container = builder.buildSearch();
    }

    @Test
    public void should_classes_be_registered() {

        var superInventory = (SuperInventory)container.find(SuperInventory.class);
        var repo = (SomeRepo)container.find(SomeRepo.class);
        var playerStats = (PlayerStats)container.find(PlayerStats.class);

        Assertions.assertNotNull(repo);
        Assertions.assertNotNull(superInventory);
        Assertions.assertNotNull(playerStats);

        Assertions.assertEquals(playerStats, superInventory.getPlayerStats());
        Assertions.assertEquals(repo, superInventory.getSomeRepo());
    }
    @Test()
    public void should_get_by_annotation()
    {
        var objects = container.findAllByAnnotation(TestingAnnotation.class);
        Assertions.assertEquals(1,objects.size());
    }

    @Test()
    public void should_get_by_interface()
    {
        var objects = container.findAllByInterface(TestingInterface.class);
        Assertions.assertEquals(1,objects.size());
    }

    @Test()
    public void should_get_by_parentClass()
    {
        var objects = container.findAllBySuperClass(BaseClassTesting.class);
        Assertions.assertEquals(1,objects.size());
    }

    @Test(expected = StackOverflowError.class)
    @Ignore
    public void should_throw_stackOverflow_exception()
    {
       // container.register(WrongUsedDependecyInjection.class, LifeTime.TRANSIENT);
    }

}
