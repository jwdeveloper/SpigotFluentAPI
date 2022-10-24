package jw.fluent_plugin.implementation.modules.mapper;

import jw.fluent_api.mapper.api.MapperProfile;
import jw.fluent_api.mapper.implementation.SimpleMapper;
import jw.fluent_api.mapper.implementation.builder.MappingProfileBuilder;

import java.util.List;

public class FluentMapperImpl implements FluentMapper
{
    private SimpleMapper simpleMapper;


    public FluentMapperImpl() {
        simpleMapper = new SimpleMapper();
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
