package unit.assets;


import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;

@Injection
public class WrongUsedDependecyInjection
{

    @Inject
    private WrongUsedDependecyInjection wrongField;
}
