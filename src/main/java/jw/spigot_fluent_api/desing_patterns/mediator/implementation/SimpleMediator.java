package jw.spigot_fluent_api.desing_patterns.mediator.implementation;

import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.Mediator;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.java.KeySet;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;

public class SimpleMediator implements Mediator {
    private final HashMap<KeySet, MediatorHandler> handlers;
    private static final String MEDIATOR_CLASS_NAME = MediatorHandler.class.getTypeName();

    public SimpleMediator() {
        handlers = new HashMap<>();
    }

    @Override
    public <Output> Output resolve(Object input, Class<Output> outputClass) {
        if (input == null)
            return null;

            var inputClass = input.getClass();
            var pair = new KeySet(inputClass, outputClass);
            var mediator = handlers.get(pair);
            if (mediator == null) {
                FluentPlugin.logError(String.format(Messages.MEDIATOR_NOT_REGISTERED, inputClass.getSimpleName()));
                return null;
            }
        try {
            return (Output) mediator.handle(input);
        } catch (Exception e) {
            FluentLogger.error("Error while executing mediator " + mediator.getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public <T extends MediatorHandler<?, ?>> void register(T mediator) {
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

        var inputClass = (Class<?>) mediatorInterface.getActualTypeArguments()[0];
        var outputClass = (Class<?>) mediatorInterface.getActualTypeArguments()[1];
        var pair = new KeySet(inputClass, outputClass);
        if (handlers.containsKey(pair)) {
            var mediator1 = handlers.get(pair);
            FluentPlugin.logError(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, inputClass, mediator1));
            return;
        }
        handlers.put(pair, mediator);
    }

    @Override
    public boolean containsPair(KeySet pair) {
        return handlers.containsKey(pair);
    }

    public Set<KeySet> getRegisteredTypes() {
        return handlers.keySet();
    }
}
