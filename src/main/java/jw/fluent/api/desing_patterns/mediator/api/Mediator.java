package jw.fluent.api.desing_patterns.mediator.api;

public interface Mediator
{
    <Output> Output resolve(Object input,Class<Output> outputClass);

     <T extends MediatorHandler<?, ?>> void register(Class<T> mediator);

    boolean containerClass(Class pair);
}
