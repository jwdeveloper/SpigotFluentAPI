package jw.spigot_fluent_api.fluent_mapper.implementation;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.mediator.implementation.Messages;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_mapper.interfaces.Mapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.MapperProfile;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.java.KeySet;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleMapper implements Mapper {

    private final HashMap<KeySet, Class<? extends MapperProfile>> mappingProfiles = new HashMap<>();
    private static final String MAPPING_PROFILE_CLASS_NAME = MapperProfile.class.getTypeName();

    public <Output> Output map(Object input, Class<Output> outputClass) {
        try {
            final var inputClass = input.getClass();
            final var keySet = new KeySet(inputClass, outputClass);
            final var mappingProfileClass = mappingProfiles.get(keySet);
            if (mappingProfileClass == null)
                return null;
            final var mappingProfile = FluentInjection.getInjection(mappingProfileClass);
            return (Output) mappingProfile.configureMapping(input);
        } catch (Exception e) {
            FluentLogger.error("Mapping exception", e);
            return null;
        }
    }

    public <Output> List<Output> map(List<?> inputs, Class<Output> outputClass) {
        final var result = new ArrayList<Output>(inputs.size());
        if (inputs.isEmpty()) {
            return result;
        }
        final var inputClass = inputs.get(0).getClass();
        final var keySet = new KeySet(inputClass, outputClass);
        final var mappingProfileClass = mappingProfiles.get(keySet);
        if (mappingProfileClass == null)
            return result;
        final var mappingProfile = FluentInjection.getInjection(mappingProfileClass);
        try {
            for (var input : inputs) {
                var mapped = (Output) mappingProfile.configureMapping(input);
                result.add(mapped);
            }
        } catch (Exception e) {
            FluentLogger.error("Mapping exception", e);
            return null;
        }
        return result;
    }


    public void registerMappingProfile(Class<? extends MapperProfile> mappingProfileClass) {
        ParameterizedType mediatorInterface = null;
        for (var _interface : mappingProfileClass.getGenericInterfaces()) {
            var name = _interface.getTypeName();
            if (name.contains(MAPPING_PROFILE_CLASS_NAME)) {
                mediatorInterface = (ParameterizedType) _interface;
                break;
            }
        }
        if (mediatorInterface == null)
            return;

        var inputClass = (Class<?>) mediatorInterface.getActualTypeArguments()[0];
        var outputClass = (Class<?>) mediatorInterface.getActualTypeArguments()[1];
        var pair = new KeySet(inputClass, outputClass);
        if (mappingProfiles.containsKey(pair)) {
            var mediator1 = mappingProfiles.get(pair);
            FluentPlugin.logError(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, inputClass, mediator1));
            return;
        }
        FluentInjection.getInjectionContainer().register(mappingProfileClass, LifeTime.SINGLETON);
        mappingProfiles.put(pair, mappingProfileClass);
    }


}
