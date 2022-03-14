package jw.spigot_fluent_api.fluent_mapper.implementation;

import jw.spigot_fluent_api.fluent_mapper.interfaces.IFluentMapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.IMap;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.ArrayList;
import java.util.List;

public class FluentMapper implements IFluentMapper {

    public <Output> Output map(IMap<?, Output> input) {
        try {
            var field = input.getClass().getField("map");
            var result = field.get(input);
            return (Output) result;
        } catch (Exception e) {
            FluentPlugin.logException("Mapping error", e);
            return null;
        }
    }

    public <Output> List<Output> map(List<?> inputs) {
        var result = new ArrayList<Output>(inputs.size());
        for (var input : inputs) {
            var mapped = map((IMap<?, Output>) input);
            result.add(mapped);
        }
        return result;
    }
}
