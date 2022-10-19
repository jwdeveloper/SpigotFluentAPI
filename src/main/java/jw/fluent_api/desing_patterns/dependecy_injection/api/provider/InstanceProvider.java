package jw.fluent_api.desing_patterns.dependecy_injection.api.provider;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

import java.util.Map;

public interface InstanceProvider
{
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections) throws Exception;
}
