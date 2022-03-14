package jw.spigot_fluent_api.desing_patterns.mediator;

public interface Mediator<Input, Output>
{
    public Output handle(Input object);
}
