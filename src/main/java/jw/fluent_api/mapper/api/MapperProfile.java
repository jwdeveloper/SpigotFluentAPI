package jw.fluent_api.mapper.api;

public interface MapperProfile<Input,Output>
{
    Output configureMapping(Input input);
}
