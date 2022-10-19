package jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.event_handler;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.EventHandler;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnInjectionEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnRegistrationEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.EventsDto;

public class EventHandlerImpl implements EventHandler {
    private final EventsDto eventsDto;

    public EventHandlerImpl(EventsDto eventsDto)
    {
       this.eventsDto = eventsDto;
    }

    @Override
    public boolean OnRegistration(OnRegistrationEvent event)
    {
        for(var fun : eventsDto.onRegistrationEvents())
        {
            var res =fun.apply(event);
            if(!res)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object OnInjection(OnInjectionEvent event)
    {
        for(var fun : eventsDto.onInjectionEvents())
        {
            var obj = fun.apply(event);
            if(obj != event.result())
            {
                return obj;
            }
        }
        return event.result();
    }
}
