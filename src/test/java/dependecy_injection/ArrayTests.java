package dependecy_injection;

import dependecy_injection.classes.Siema1;
import dependecy_injection.classes.Siema2;
import dependecy_injection.classes.Test1;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import org.junit.Test;

import java.util.List;

public class ArrayTests
{

    @Test
    public void a()
    {

      var a = Test1.class;

      var con = a.getConstructors()[0];

      for(var p : con.getGenericParameterTypes())
      {
          var i =0;
      }
        for(var p : con.getParameters())
        {
            var i =0;
        }
        for(var p : con.getParameterTypes())
        {
            var i =0;
        }
    }

    @Test
    public void ShouldResolveArray()
    {

        var container = new Container();
        container.register(Test1.class, LifeTime.SINGLETON);
        container.register(Siema1.class, LifeTime.SINGLETON);
        container.register(Siema2.class, LifeTime.SINGLETON);

        FluentInjection.setInjectionContainer(container);


        var result = FluentInjection.getInjection(Test1.class);
        result.show();
    }










}
