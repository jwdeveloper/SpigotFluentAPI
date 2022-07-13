package jw.spigot_fluent_api.fluent_mapper.api;

public interface MapperProfile<Input,Output>
{
    Output configureMapping(Input input);
}
