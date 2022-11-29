package jw.fluent_plugin.implementation.modules.mapper;

import jw.fluent_api.mapper.api.MapperProfile;
import jw.fluent_api.mapper.implementation.SimpleMapper;
import jw.fluent_api.mapper.implementation.builder.MappingProfileBuilder;

import java.util.List;
import java.util.function.Function;

public class FluentMapperImpl implements FluentMapper
{
    private SimpleMapper simpleMapper;


    public FluentMapperImpl(Function<Class<?>,Object> typeResolver) {
        simpleMapper = new SimpleMapper(typeResolver);
    }


    public  <Input,Output> Output map(Input input, Class<Output> outputClass)
    {
        return simpleMapper.map(input,outputClass);
    }

    public  <Output> List<Output> mapAll(List<?> inputs, Class<Output> outputClass) {
        return simpleMapper.map(inputs,outputClass);
    }

    public MappingProfileBuilder create()
    {
        return new MappingProfileBuilder();
    }

    public void register(Class<MapperProfile<?,?>> mappingProfile)
    {
       simpleMapper.registerMappingProfile(mappingProfile);
    }
}
