package jw.fluent_api.desing_patterns.mediator;

import jw.fluent_api.desing_patterns.mediator.implementation.SimpleMediator;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;

public class FluentMediator {
    static {
        instance = new FluentMediatorFasade();
    }
    private static final FluentMediatorFasade instance;


    public static FluentMediatorFasade getInstance()
    {
        return instance;
    }
    public static <Output> Output resolve(Object input,Class<Output> outputClass) {
        return instance.resolve(input, outputClass);
    }

    public static <T extends MediatorHandler<?, ?>> void register(T mediator) {
        instance.register(mediator);
    }
}
