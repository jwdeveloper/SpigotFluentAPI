package integration.desing_patterns.decorator;

import integration.desing_patterns.decorator.example_classes.ExampleDecorator;
import integration.desing_patterns.decorator.example_classes.ExampleDecorator2;
import integration.desing_patterns.decorator.example_classes.ExampleInterface;
import integration.desing_patterns.decorator.example_classes.ExampleClass;
import jw.fluent_api.desing_patterns.decorator.FluentDecorator;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import org.junit.Assert;
import org.junit.Test;

public class DecoratorTests
{
    @Test
    public void Should_DecoratorWork() throws Exception {
        //Arrange
        var containerBuilder = new BuilderTemp();
        containerBuilder.register(ExampleInterface.class, ExampleClass.class, LifeTime.SINGLETON);

        var decoratorBuilder = FluentDecorator.CreateDecorator();
        decoratorBuilder.decorate(ExampleInterface.class, ExampleDecorator.class);
        decoratorBuilder.decorate(ExampleInterface.class, ExampleDecorator2.class);

        var decorator = decoratorBuilder.build();
        containerBuilder.configure(c -> c.getEvents().add(decorator));

        var container = containerBuilder.buildDefault();
        var exampleInterface = (ExampleInterface)container.find(ExampleInterface.class);

        //Act
        var result = exampleInterface.doSomething();

        //Assert
        Assert.assertEquals(exampleInterface.getClass(), ExampleDecorator2.class);
        Assert.assertEquals("ExampleDecorator2 ExampleDecorator ExampleClass ",result);
    }

    public class BuilderTemp extends ContainerBuilderImpl<BuilderTemp>
    {

    }

}
