package code_generator;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.provider.InstanceProviderImpl;
import jw.spigot_fluent_api.utilites.code_generator.UnitTestGenerator;
import org.junit.Test;

public class GenerateUnitTest
{
    @Test
    public void test()
    {
        UnitTestGenerator.generate(InstanceProvider.class, InstanceProviderImpl.class,"D:\\tmp");
    }
}
