package integration.desing_patterns.decorator.example_classes;

public class ExampleClass implements ExampleInterface {
    @Override
    public String doSomething() {
        return this.getClass().getSimpleName()+" ";
    }
}
