package desing_patterns.mediator;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.desing_patterns.mediator.Mediator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MediatorTest extends SpigotTestBase implements Mediator<Integer,String>
{
    FluentMediator fluentMediator;

    @Before
    public void init()
    {
        super.init();
        fluentMediator = new FluentMediator();
        fluentMediator.register(this);
    }

    @Test()
    public void should_not_resolve()
    {
        String s = "some value";
        var result  = fluentMediator.resolve(s);
        Assertions.assertNull(result);
    }

    @Test
    public void should_resolve()
    {
        Integer i = 12;
        String result  = fluentMediator.resolve(i);
        Assertions.assertEquals("THIS TEST IS WORKING "+i.toString(),result);
    }

    @Override
    public String handle(Integer object) {
        return "THIS TEST IS WORKING "+object.toString();
    }
}
