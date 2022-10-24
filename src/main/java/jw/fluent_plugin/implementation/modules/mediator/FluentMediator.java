package jw.fluent_plugin.implementation.modules.mediator;

public interface FluentMediator
{
    public <Output> Output resolve(Object input, Class<Output> outputClass);
}

