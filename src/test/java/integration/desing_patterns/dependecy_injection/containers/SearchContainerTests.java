package integration.desing_patterns.dependecy_injection.containers;

import integration.desing_patterns.dependecy_injection.example_classes.*;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import org.junit.Assert;
import org.junit.Test;

public class SearchContainerTests
{
  /*  @Test
    public void Should_Find_By_Interface() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);
        builder.register(ExampleInterfaceV2.class, ExampleClassV2.class, LifeTime.SINGLETON);
        var container = builder.buildSearch();
        //Act
        var instances = container.findAllByInterface(ExampleCommonInterface.class);

        //Assert
        Assert.assertEquals(2, instances.size());
    }

    @Test
    public void Should_Find_By_SuperClass() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);
        builder.register(ExampleInterfaceV2.class, ExampleClassV2.class, LifeTime.SINGLETON);
        var container = builder.buildSearch();
        //Act
        var instances = container.findAllBySuperClass(ExampleSuperClass.class);

        //Assert
        Assert.assertEquals(2, instances.size());
    }

    @Test
    public void Should_Find_By_Annotation() throws Exception {
        //Arrange
        var builder = new ContainerBuilderImpl();
        builder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);
        builder.register(ExampleInterfaceV2.class, ExampleClassV2.class, LifeTime.SINGLETON);
        var container = builder.buildSearch();

        //Act
        var instances = container.findAllByAnnotation(ExampleAnnotation.class);

        //Assert
        Assert.assertEquals(2, instances.size());
    }*/
}
