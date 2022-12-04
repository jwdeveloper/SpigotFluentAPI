package jw.fluent.api.desing_patterns.dependecy_injection.api.events;

import jw.fluent.api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent.api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;

public interface EventHandler extends ContainerEvents
{
    public boolean OnRegistration(OnRegistrationEvent event);
    public Object OnInjection(OnInjectionEvent event) throws Exception;
}
