package jw.fluent.api.desing_patterns.dependecy_injection.api.provider;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

import java.util.Map;

public interface InstanceProvider
{
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Container container) throws Exception;
}
