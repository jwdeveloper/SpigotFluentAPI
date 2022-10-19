package jw.fluent_api.desing_patterns.dependecy_injection.api.models;


import jw.fluent_api.desing_patterns.dependecy_injection.api.events.ContainerEvents;

import java.util.List;

public record EventsDto(List<ContainerEvents> events)

{

}
