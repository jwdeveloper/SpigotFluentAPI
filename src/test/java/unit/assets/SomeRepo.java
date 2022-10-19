package unit.assets;

import unit.assets.interfaces.TestingInterface;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;

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
