package integration.desing_patterns.dependecy_injection.containers;

import integration.desing_patterns.dependecy_injection.example_classes.ExampleClass;
import integration.desing_patterns.dependecy_injection.example_classes.ExampleInterface;
import integration.desing_patterns.dependecy_injection.example_classes.ExampleWithList;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DefaultContainerTests
{
    @Test
    public void Should_Register_Implementation_Singletone() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleClass.class, LifeTime.SINGLETON);

       //Act
        var container = builder.buildDefault();
        var instance = container.find(ExampleClass.class);
        var instance2 = container.find(ExampleClass.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertEquals(instance, instance2);
    }

    @Test
    public void Should_Register_Implementation_Transient() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleClass.class, LifeTime.TRANSIENT);

        //Act
        var container = builder.buildDefault();
        var instance = container.find(ExampleClass.class);
        var instance2 = container.find(ExampleClass.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertNotEquals(instance, instance2);
    }

    @Test
    public void Should_Register_Provider_Singletone() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        var object = new ExampleClass();
        builder.register(ExampleInterface.class, LifeTime.SINGLETON, (a)->
        {
            return object;
        });

        //Act
        var container = builder.buildDefault();
        var instance = container.find(ExampleInterface.class);
        var instance2 = container.find(ExampleInterface.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertEquals(instance, object);
        Assert.assertEquals(instance, instance2);
    }

    @Test
    public void Should_Register_Provider_Transient() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, LifeTime.TRANSIENT, (a)->
        {
            return new ExampleClass();
        });

        //Act
        var container = builder.buildDefault();
        var instance = container.find(ExampleInterface.class);
        var instance2 = container.find(ExampleInterface.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertNotEquals(instance, instance2);
    }

    @Test
    public void Should_Register_InterfaceAndImplementation_Singletone() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);

        //Act
        var container = builder.buildDefault();
        var instance = container.find(ExampleInterface.class);
        var instance2 = container.find(ExampleInterface.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertEquals(instance, instance2);
    }

    @Test
    public void Should_Register_InterfaceAndImplementation_Transient() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.TRANSIENT);

        //Act
        var container = builder.buildDefault();
        var instance = container.find(ExampleInterface.class);
        var instance2 = container.find(ExampleInterface.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertNotEquals(instance, instance2);
    }


    @Test
    public void Should_Register_List_Transient() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.registerSigleton(ExampleClass.class);
        builder.registerList(ExampleInterface.class, LifeTime.TRANSIENT);
        builder.register(ExampleWithList.class, LifeTime.TRANSIENT);

        //Act
        var container = builder.buildDefault();
        var instance = (ExampleWithList)container.find(ExampleWithList.class);

        //Assert
        Assert.assertNotNull(instance);
        Assert.assertEquals(1,instance.getExampleInterfaces().size());
      ;
    }
}



