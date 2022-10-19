package jw.fluent_api.desing_patterns.dependecy_injection.api.events.events;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;

public record OnRegistrationEvent(Class<?> input, InjectionInfo injectionInfo, RegistrationInfo registrationInfo) {
}
