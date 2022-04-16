package jw.spigot_fluent_api.fluent_mapper;

import jw.spigot_fluent_api.fluent_mapper.implementation.SimpleMapper;
import jw.spigot_fluent_api.fluent_mapper.implementation.builder.MappingProfileBuilder;
import jw.spigot_fluent_api.fluent_mapper.interfaces.MapperProfile;

import java.util.List;

public class FluentMapper {
    private static final FluentMapper instance;
    private SimpleMapper simpleMapper;

    static {
        instance = new FluentMapper();
    }

    FluentMapper() {
        simpleMapper = new SimpleMapper();
    }


    public static <Input,Output> Output map(Input input, Class<Output> outputClass)
    {
        return instance.simpleMapper.map(input,outputClass);
    }

    public static <Output> List<Output> mapAll(List<?> inputs, Class<Output> outputClass) {
        return instance.simpleMapper.map(inputs,outputClass);
    }

    public static MappingProfileBuilder create()
    {
        return new MappingProfileBuilder(instance.simpleMapper);
    }

    public static void registerMappingProfile(Class<MapperProfile> mappingProfile)
    {
       instance.simpleMapper.registerMappingProfile(mappingProfile);
    }
}
