package unit.assets;


import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;

@Injection
public class WrongUsedDependecyInjection
{

    @Inject
    private WrongUsedDependecyInjection wrongField;
}
