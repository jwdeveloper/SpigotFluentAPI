package jw.spigot_fluent_api.desing_patterns.mediator.implementation;

import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import jw.spigot_fluent_api.utilites.java.KeySet;

import java.util.Collection;
import java.util.Set;

public class FluentMediator {
    static {
        instance = new FluentMediator();
    }
    private static final FluentMediator instance;
    private final SimpleMediator simpleMediator;

     FluentMediator() {
         simpleMediator = new SimpleMediator();
    }

    public static <Output> Output resolve(Object input,Class<Output> outputClass) {
        return instance.simpleMediator.resolve(input, outputClass);
    }

    public static <T extends MediatorHandler<?, ?>> void register(T mediator) {
        instance.simpleMediator.register(mediator);
    }
}
