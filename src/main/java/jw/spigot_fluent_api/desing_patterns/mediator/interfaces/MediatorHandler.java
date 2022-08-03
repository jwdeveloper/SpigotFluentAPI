package jw.spigot_fluent_api.desing_patterns.mediator.interfaces;

public interface MediatorHandler<Input, Output>
{
    public Output handle(Input request);
}
