package jw.spigot_fluent_api.desing_patterns.mediator;

public interface Mediator<A,B>
{
    public B handle(A object);
}
