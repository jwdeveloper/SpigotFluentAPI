package jw.spigot_fluent_api.desing_patterns.mediator;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FluentMediator {
    static {
        instance = new FluentMediator();
    }

    private final HashMap<Class<?>, Mediator> mediators;

    private static final FluentMediator instance;
    private static final String MEDIATOR_CLASS_NAME = Mediator.class.getTypeName();

    public FluentMediator() {
        mediators = new HashMap<>();
    }

    public static <Output> Output resolve(Object input) {
        if (input == null)
            return null;

        var mediators = instance.mediators;
        var _class = input.getClass();
        if (!mediators.containsKey(_class)) {
            FluentPlugin.logError(String.format(Messages.MEDIATOR_NOT_REGISTERED, _class.getSimpleName()));
            return null;
        }
        var mediator = mediators.get(_class);
        try {
            return (Output) mediator.handle(input);
        } catch (Exception e) {
            FluentPlugin.logException("Error while executing mediator " + mediator.getClass().getSimpleName(), e);
            return null;
        }
    }

    public static <T extends Mediator> void registerAll(Collection<T> mediators) {
        for (var mediator : mediators) {
            register(mediator);
        }
    }

    public static <T extends Mediator<?, ?>> void register(T mediator) {
        ParameterizedType mediatorInterface = null;
        for (var _interface : mediator.getClass().getGenericInterfaces()) {
            var name = _interface.getTypeName();
            if (name.contains(MEDIATOR_CLASS_NAME)) {
                mediatorInterface = (ParameterizedType) _interface;
                break;
            }
        }
        if (mediatorInterface == null)
            return;

        var invokerClass = (Class<?>) mediatorInterface.getActualTypeArguments()[0];
        var mediators = instance.mediators;
        if (mediators.containsKey(invokerClass)) {
            var mediator1 = mediators.get(invokerClass);
            FluentPlugin.logError(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, invokerClass, mediator1));
            return;
        }
        mediators.put(invokerClass, mediator);
    }

    public static Set<Class<?>> getRegisteredTypes()
    {
        return instance.mediators.keySet();
    }
}
