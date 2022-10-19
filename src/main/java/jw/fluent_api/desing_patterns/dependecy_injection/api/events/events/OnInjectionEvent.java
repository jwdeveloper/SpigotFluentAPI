package jw.fluent_api.desing_patterns.dependecy_injection.api.events.events;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

import java.util.Map;

public record OnInjectionEvent(Class<?> input, InjectionInfo injectionInfo, Object result, Map<Class<?>, InjectionInfo> injectionInfoMap)
{

}
