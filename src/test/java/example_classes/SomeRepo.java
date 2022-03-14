package example_classes;

import example_classes.interfaces.TestingInterface;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;

import java.util.List;

@Injection
public class SomeRepo implements TestingInterface
{
    private List<String> data;

    private String name = "REPOSITORY";


    @Override
    public void doSomething() {

    }
}
