package dependecy_injection.classes;

import dependecy_injection.ArrayTests;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;

import java.util.List;


@Injection
public class Test1
{
    private List<Siema> test2s;

    @Inject
    public Test1(List<Siema> tests)
    {
        this.test2s = tests;
    }

    public void show()
    {
        FluentLogger.log("Movie");
        test2s.forEach(Siema::say);
    }

}