package unit.desing_patterns.mediator;
import jw.fluent_api.desing_patterns.mediator.implementation.SimpleMediator;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

public class MediatorTest extends SpigotTestBase
{
    private SimpleMediator mediator;

    @Before
    public void init()
    {
        var exampleMediatorClass = new MediatorClassExample();
        Function<Class<?>,Object> containerResolver = (e) ->
        {
            if(e == exampleMediatorClass.getClass())
            {
                return exampleMediatorClass;
            }
            return null;
        };

        mediator = new SimpleMediator(containerResolver);
        mediator.register(exampleMediatorClass.getClass());

        var result = mediator.getRegisteredTypes();
        Assertions.assertEquals(1, result.size());
    }

    @Test()
    public void should_not_resolve()
    {
        String input = "VALUE THAT IS NOT AN INTEGER";
        String result  = mediator.resolve(input, String.class);
        Assertions.assertNull(result);
    }

    @Test()
    public void shoud_not_register_twice()
    {
        var instance = new MediatorClassExample();
        mediator.register(instance.getClass());
        var result = mediator.getRegisteredTypes();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void should_resolve()
    {
        Integer input = 2137;
        String result  = mediator.resolve(input, String.class);
        Assertions.assertEquals(MediatorClassExample.TESTOUTPUT,result);
    }
}
