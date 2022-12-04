package jw.fluent.api.mapper.api;

public interface MapperProfile<Input,Output>
{
    Output configureMapping(Input input);
}
