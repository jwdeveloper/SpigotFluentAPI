package jw.fluent_api.mapper.implementation;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_api.desing_patterns.mediator.implementation.Messages;
import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.mapper.api.Mapper;
import jw.fluent_api.mapper.api.MapperProfile;
import jw.fluent_api.utilites.java.KeySet;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;
import lombok.SneakyThrows;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleMapper implements Mapper {

    private final HashMap<KeySet, Class<? extends MapperProfile>> mappingProfiles;
    private final String MAPPING_PROFILE_CLASS_NAME;

    private FluentInjection injection;

    public SimpleMapper()
    {
        mappingProfiles = new HashMap<>();
        MAPPING_PROFILE_CLASS_NAME = MapperProfile.class.getTypeName();
        injection = FluentAPI.injection();
    }


    public <Output> Output map(Object input, Class<Output> outputClass) {
        try {
            final var inputClass = input.getClass();
            final var keySet = new KeySet(inputClass, outputClass);
            final var mappingProfileClass = mappingProfiles.get(keySet);
            if (mappingProfileClass == null)
                return null;
            final var mappingProfile = injection.findInjection(mappingProfileClass);
            return (Output) mappingProfile.configureMapping(input);
        } catch (Exception e) {
            OldLogger.error("Mapping exception", e);
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
        final var mappingProfile = injection.findInjection(mappingProfileClass);
        try {
            for (var input : inputs) {
                var mapped = (Output) mappingProfile.configureMapping(input);
                result.add(mapped);
            }
        } catch (Exception e) {
            OldLogger.error("Mapping exception", e);
            return null;
        }
        return result;
    }


    @SneakyThrows
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
            OldLogger.info(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, inputClass, mediator1));
            return;
        }
        var registrationInfo = new RegistrationInfo(null,mappingProfileClass,null,LifeTime.SINGLETON, RegistrationType.OnlyImpl);
        ((FluentInjectionImpl)injection).getContainer().register(registrationInfo);
        mappingProfiles.put(pair, mappingProfileClass);
    }


}
