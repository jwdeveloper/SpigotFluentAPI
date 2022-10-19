package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

public record OnInjectionEvent(Class<?> input, InjectionInfo injectionInfo, Object result)
{

}
