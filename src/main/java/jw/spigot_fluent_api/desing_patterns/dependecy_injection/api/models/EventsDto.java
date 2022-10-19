package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models;


import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnInjectionEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnRegistrationEvent;

import java.util.List;
import java.util.function.Function;

public record EventsDto(List<Function<OnRegistrationEvent,Boolean>> onRegistrationEvents,
                        List<Function<OnInjectionEvent,Object>> onInjectionEvents
                        )
{

}
