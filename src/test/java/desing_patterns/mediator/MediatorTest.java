package desing_patterns.mediator;
import jw.spigot_fluent_api.desing_patterns.mediator.implementation.SimpleMediator;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MediatorTest extends SpigotTestBase implements MediatorHandler<Integer,String>
{
    SimpleMediator mediator;

    @Before
    public void init()
    {
        super.init();
        mediator = new SimpleMediator();
        mediator.register(this);
    }

    @Test()
    public void should_not_resolve()
    {
        String s = "some value";
        var result  = mediator.resolve(s,String.class);
        Assertions.assertNull(result);
    }

    @Test
    public void should_resolve()
    {
        Integer i = 12;
        String result  = mediator.resolve(i,String.class);
        Assertions.assertEquals("THIS TEST IS WORKING "+i.toString(),result);
    }

    @Override
    public String handle(Integer object) {
        return "THIS TEST IS WORKING "+object.toString();
    }
}
