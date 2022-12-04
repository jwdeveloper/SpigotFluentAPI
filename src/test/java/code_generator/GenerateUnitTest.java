package code_generator;

import jw.fluent.api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.provider.InstanceProviderImpl;
import jw.fluent.api.utilites.code_generator.UnitTestGenerator;
import org.junit.Ignore;
import org.junit.Test;

public class GenerateUnitTest
{
    @Test
    @Ignore
    public void test()
    {
        UnitTestGenerator.generate(InstanceProvider.class, InstanceProviderImpl.class,"D:\\tmp");
    }
}
