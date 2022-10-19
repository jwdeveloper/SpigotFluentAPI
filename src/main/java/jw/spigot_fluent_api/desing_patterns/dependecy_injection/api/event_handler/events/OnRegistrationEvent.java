package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;

public record OnRegistrationEvent(Class<?> input, InjectionInfo injectionInfo, RegistrationInfo registrationInfo) {
}
