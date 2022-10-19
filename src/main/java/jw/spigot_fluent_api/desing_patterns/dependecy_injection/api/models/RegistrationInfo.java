package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;

import java.util.function.Function;

public record RegistrationInfo(Class<?> _interface,
                               Class<?> implementation,
                               Function<Object,Object> provider,
                               LifeTime lifeTime,
                               RegistrationType registrationType)
{

}
