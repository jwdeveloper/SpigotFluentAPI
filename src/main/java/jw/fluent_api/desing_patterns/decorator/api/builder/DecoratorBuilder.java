package jw.fluent_api.desing_patterns.decorator.api.builder;

import jw.fluent_api.desing_patterns.decorator.api.Decorator;

public interface DecoratorBuilder {
    <T> DecoratorBuilder decorate(Class<T> _interface, Class<? extends T> implementation);
    Decorator build() throws Exception;
}
