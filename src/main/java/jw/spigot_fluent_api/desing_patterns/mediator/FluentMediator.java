package jw.spigot_fluent_api.desing_patterns.mediator;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;

public class FluentMediator
{
    static
    {
        instance = new FluentMediator();
    }

    private final HashMap<Class<?>,Mediator> mediators;

    private static FluentMediator instance;

    public FluentMediator()
    {
        mediators = new HashMap<>();
    }

    public static  <T> T resolve(Object _object)
    {
        if(_object == null)
            return null;

        var mediators = instance.mediators;
        var _class = _object.getClass();
        if(!mediators.containsKey(_class))
        {
            FluentPlugin.logError(String.format(Messages.MEDIATOR_NOT_REGISTERED,_class.getSimpleName()));
            return null;
        }
        var mediator = mediators.get(_object.getClass());
        try
        {
            return (T)mediator.handle(_object);
        }
        catch (Exception e)
        {
            FluentPlugin.logException("Error while executing mediator "+mediator.getClass().getSimpleName(),e);
        }
         return null;
    }

    public static  <T extends Mediator> void registerAll(Collection<T> mediators)
    {
        for(var mediator : mediators)
        {
            register(mediator);
        }
    }

    public static  <T extends Mediator> void register(T mediator)
    {
        ParameterizedType mediatorInterface = null;
        for(var _interface : mediator.getClass().getGenericInterfaces())
        {
            var name= _interface.getTypeName();

            if(name.contains(Mediator.class.getTypeName()))
            {
                mediatorInterface = (ParameterizedType)_interface;
                break;
            }
        }
        if(mediatorInterface == null)
            return;

        var invokerClass =  (Class<?>)mediatorInterface.getActualTypeArguments()[0];
        var mediators = instance.mediators;
        if(mediators.containsKey(invokerClass))
        {
            var med = mediators.get(invokerClass);
            FluentPlugin.logError("Type "+invokerClass+" is already register from Mediator "+med);
            return;
        }
        mediators.put(invokerClass,mediator);
    }
}
