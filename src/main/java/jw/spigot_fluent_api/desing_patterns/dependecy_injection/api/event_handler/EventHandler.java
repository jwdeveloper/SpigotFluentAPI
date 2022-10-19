package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnInjectionEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnRegistrationEvent;

public interface EventHandler
{
    public boolean OnRegistration(OnRegistrationEvent event);
    public Object OnInjection(OnInjectionEvent event);
}
