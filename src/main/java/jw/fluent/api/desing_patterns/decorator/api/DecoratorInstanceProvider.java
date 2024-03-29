package jw.fluent.api.desing_patterns.decorator.api;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

import java.util.Map;

public interface DecoratorInstanceProvider
{
    Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Object toSwap, Container container) throws Exception;

}
