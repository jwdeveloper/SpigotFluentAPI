package integration.desing_patterns.decorator.example_classes;

import jw.fluent_api.desing_patterns.decorator.api.annoatations.Decorator;

@Decorator
public class ExampleDecorator implements ExampleInterface
{
    private final ExampleInterface exampleInterface;


    public ExampleDecorator(ExampleInterface exampleInterface) {
        this.exampleInterface = exampleInterface;
    }

    @Override
    public String doSomething() {
        return getClass().getSimpleName()+" "+exampleInterface.doSomething();
    }
}
