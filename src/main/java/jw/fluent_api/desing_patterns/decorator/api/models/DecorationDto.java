package jw.fluent_api.desing_patterns.decorator.api.models;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

import java.util.List;

public record DecorationDto(Class<?> _interface, List<InjectionInfo> implementations)
{

}