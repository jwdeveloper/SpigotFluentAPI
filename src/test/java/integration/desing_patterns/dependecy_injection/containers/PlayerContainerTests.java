package integration.desing_patterns.dependecy_injection.containers;

import be.seeseemelk.mockbukkit.MockBukkit;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import integration.desing_patterns.dependecy_injection.example_classes.ExampleClass;
import integration.desing_patterns.dependecy_injection.example_classes.ExampleClassV2;
import integration.desing_patterns.dependecy_injection.example_classes.ExampleInterface;
import integration.desing_patterns.dependecy_injection.example_classes.ExampleInterfaceV2;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent.plugin.api.extention.FluentApiExtentionsManager;
import jw.fluent.plugin.implementation.FluentApiContainerBuilderImpl;
import jw.fluent.plugin.implementation.modules.player_context.implementation.FluentPlayerContext;
import jw.fluent.plugin.implementation.modules.player_context.implementation.FluentPlayerContextListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class PlayerContainerTests {
    private static JavaPlugin plugin;

    @BeforeClass
    public static void before() {
        MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin();
    }

    @Test
    public void Should_Find_By_UUID() throws Exception {
        //Arrange
        var playerUuid = UUID.randomUUID();
        var extentionsManagerMock = Mockito.mock(FluentApiExtentionsManager.class);

        var builder = new FluentApiContainerBuilderImpl(extentionsManagerMock, plugin);
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);
        builder.register(ExampleInterfaceV2.class, ExampleClassV2.class, LifeTime.TRANSIENT);
        var container = builder.build();

        var playerContainer = new FluentPlayerContext(container, new ArrayList<>(), new FluentPlayerContextListener());

        //Act
        var instancesSingleTone = (ExampleInterface) playerContainer.find(ExampleInterface.class, playerUuid);
        var instancesSingleTone2 = (ExampleInterface) playerContainer.find(ExampleInterface.class, playerUuid);

        var instancesTransient = (ExampleInterfaceV2) playerContainer.find(ExampleInterfaceV2.class, playerUuid);
        var instancesTransient2 = (ExampleInterfaceV2) playerContainer.find(ExampleInterfaceV2.class, playerUuid);
        //Assert
        Assert.assertEquals(instancesSingleTone, instancesSingleTone2);
        Assert.assertNotEquals(instancesTransient, instancesTransient2);
    }

    @Test
    public void Should_DifferentPlayer_Should_Has_DifferentObjects() throws Exception {
        //Arrange
        var player1 = UUID.randomUUID();
        var player2 = UUID.randomUUID();
        var extentionsManagerMock = Mockito.mock(FluentApiExtentionsManager.class);
        var builder = new FluentApiContainerBuilderImpl(extentionsManagerMock, plugin);
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);


        var container = builder.build();

        var playerInjection = new RegistrationInfo(
                ExampleInterfaceV2.class,
                ExampleClassV2.class,
                null,
                LifeTime.SINGLETON,
                RegistrationType.InterfaceAndIml);

        var playerContainer = new FluentPlayerContext(container, Arrays.asList(playerInjection), new FluentPlayerContextListener());
        //Act
        var player1Instance = (ExampleInterface) playerContainer.find(ExampleInterface.class, player1);
        var player2Instance = (ExampleInterface) playerContainer.find(ExampleInterface.class, player2);

        var player1OwnInstance = (ExampleInterfaceV2) playerContainer.find(ExampleInterfaceV2.class, player1);
        var player2OwnInstance = (ExampleInterfaceV2) playerContainer.find(ExampleInterfaceV2.class, player2);

        //Assert
        Assert.assertEquals(player1Instance, player2Instance);
        Assert.assertNotEquals(player1OwnInstance, player2OwnInstance);
    }
}
