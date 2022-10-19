package integration.desing_patterns.dependecy_injection.containers;

import integration.desing_patterns.dependecy_injection.example_classes.*;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class PlayerContainerTests
{
    @Test
    public void Should_Find_By_UUID() throws Exception {
        //Arrange
        var playerUuid = UUID.randomUUID();
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);
        builder.register(ExampleInterfaceV2.class, ExampleClassV2.class, LifeTime.TRANSIENT);
        var container = builder.buildPlayer();
        //Act
        var instancesSingleTone = (ExampleInterface)container.find(ExampleInterface.class, playerUuid);
        var instancesSingleTone2 = (ExampleInterface)container.find(ExampleInterface.class, playerUuid);

        var instancesTransient = (ExampleInterfaceV2)container.find(ExampleInterfaceV2.class, playerUuid);
        var instancesTransient2 = (ExampleInterfaceV2)container.find(ExampleInterfaceV2.class, playerUuid);
        //Assert
        Assert.assertEquals(instancesSingleTone, instancesSingleTone2);
        Assert.assertNotEquals(instancesTransient, instancesTransient2);
    }

    @Test
    public void Should_DifferentPlayer_Should_Has_DifferentObjects() throws Exception {
        //Arrange
        var player1 = UUID.randomUUID();
        var player2 = UUID.randomUUID();
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);
        var container = builder.buildPlayer();
        //Act
        var player1Instance = (ExampleInterface)container.find(ExampleInterface.class, player1);
        var player2Instance = (ExampleInterface)container.find(ExampleInterface.class, player2);

        //Assert
        Assert.assertNotEquals(player1Instance, player2Instance);
    }
}
