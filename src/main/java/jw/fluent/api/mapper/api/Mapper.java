package jw.fluent.api.mapper.api;

import java.util.List;

public interface Mapper
{
     <Output> Output map(Object input, Class<Output> outputClass);
     <Output> List<Output> map(List<?> inputs, Class<Output> outputClass);
      void registerMappingProfile(Class<? extends MapperProfile> mappingProfileType);
}
