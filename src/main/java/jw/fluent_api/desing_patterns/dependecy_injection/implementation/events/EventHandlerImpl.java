package jw.fluent_api.desing_patterns.dependecy_injection.implementation.events;

import jw.fluent_api.desing_patterns.dependecy_injection.api.events.ContainerEvents;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.EventHandler;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;

import java.util.List;

public class EventHandlerImpl implements EventHandler {
    private final List<ContainerEvents> events;

    public EventHandlerImpl(List<ContainerEvents> events)
    {
       this.events = events;
    }

    @Override
    public boolean OnRegistration(OnRegistrationEvent event)
    {
        for(var handler : events)
        {
            var res =handler.OnRegistration(event);
            if(!res)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object OnInjection(OnInjectionEvent event) throws Exception {
        for(var handler : events)
        {
            var obj = handler.OnInjection(event);
            if(obj != event.result())
            {
                return obj;
            }
        }
        return event.result();
    }
}
