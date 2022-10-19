package jw.spigot_fluent_api.desing_patterns.decorator.implementation;

import jw.spigot_fluent_api.desing_patterns.decorator.FluentDecorator;
import jw.spigot_fluent_api.desing_patterns.decorator.api.models.DecorationDto;

import java.util.ArrayList;
import java.util.Map;

public class DecoratorImpl
{
    private Map<Class<?>, DecorationDto> decorators;

   /* public static <T> void decorate(Class<T> implementation, Class<? extends T> decoration) throws Exception {
        var instance = getInstance();
        var dto = instance.decorators.get(implementation);
        if(dto == null)
        {
            dto  = new FluentDecorator.DecorationDto(new ArrayList<>());
            dto.implementations.add(decoration);
            return;
        }

        var alreadyAdded = dto.implementations.stream().filter(c -> c.equals(decoration)).findFirst();
        if(alreadyAdded.isPresent())
        {
            throw new Exception(decoration.getSimpleName()+" is already decorating "+implementation.getSimpleName());
        }

        dto.implementations.add(decoration);
    }*/
}
