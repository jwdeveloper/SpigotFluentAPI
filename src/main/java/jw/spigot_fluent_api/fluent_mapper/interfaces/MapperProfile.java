package jw.spigot_fluent_api.fluent_mapper.interfaces;

public interface MapperProfile<Input,Output>
{
    Output configureMapping(Input input);
}
