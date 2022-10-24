package jw.fluent_api.desing_patterns.mediator.api;

import jw.fluent_api.utilites.java.KeySet;

public interface Mediator
{
    <Output> Output resolve(Object input,Class<Output> outputClass);

    <T extends MediatorHandler<?, ?>> void register(T mediator);

    boolean containsPair(KeySet pair);
}