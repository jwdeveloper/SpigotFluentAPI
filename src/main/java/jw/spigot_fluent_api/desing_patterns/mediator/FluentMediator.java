package jw.spigot_fluent_api.desing_patterns.mediator;

import jw.spigot_fluent_api.desing_patterns.mediator.implementation.SimpleMediator;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;

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
