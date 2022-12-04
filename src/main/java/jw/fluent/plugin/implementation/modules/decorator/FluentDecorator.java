package jw.fluent.plugin.implementation.modules.decorator;

import jw.fluent.api.desing_patterns.decorator.api.builder.DecoratorBuilder;
import jw.fluent.api.desing_patterns.decorator.implementation.DecoratorBuilderImpl;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.factory.InjectionInfoFactoryImpl;

import java.util.*;

public class FluentDecorator
{
    public static DecoratorBuilder CreateDecorator()
    {
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        return new DecoratorBuilderImpl(injectionInfoFactory, new HashMap<>());
    }

}
