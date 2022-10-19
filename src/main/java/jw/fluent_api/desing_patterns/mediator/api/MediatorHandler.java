package jw.fluent_api.desing_patterns.mediator.api;

public interface MediatorHandler<Input, Output>
{
    public Output handle(Input request);
}
