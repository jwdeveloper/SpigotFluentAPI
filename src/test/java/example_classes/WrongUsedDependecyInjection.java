package example_classes;


import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;

@Injection
public class WrongUsedDependecyInjection
{

    @Inject
    private WrongUsedDependecyInjection wrongField;
}
