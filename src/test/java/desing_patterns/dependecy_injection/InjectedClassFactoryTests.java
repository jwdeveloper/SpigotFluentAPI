package desing_patterns.dependecy_injection;

import assets.BaseClassTesting;
import assets.SuperInventory;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.factory.InjectedClassFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class InjectedClassFactoryTests
{
    @Before
    public void init() {

    }


    @Test
    public void should_create_injection() throws Exception {

       var injection  = InjectedClassFactory.getFromClass(SuperInventory.class,LifeTime.SINGLETON);
        Assertions.assertNotNull(injection);
    }

    @Test(expected = Exception.class)
    public void should_not_create_injection() throws Exception {

        var injection  = InjectedClassFactory.getFromClass(BaseClassTesting.class,LifeTime.SINGLETON);
        Assertions.assertNull(injection);
    }
}
