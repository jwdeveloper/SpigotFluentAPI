package types;

import jw.spigot_fluent_api.utilites.ClassTypeUtility;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class LoadingClassesTest
{
    @Test
    public void shouldLoadClasses()
    {
       var classes = ClassTypeUtility.findAllClassesUsingClassLoader(this.getClass().getPackageName());


        Assertions.assertNotNull(classes);
    }
}
