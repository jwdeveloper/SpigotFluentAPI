package jw.fluent_api.desing_patterns.mediator.implementation;

import jw.fluent_api.desing_patterns.mediator.api.Mediator;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.utilites.java.KeySet;
import jw.fluent_api.utilites.java.Pair;

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
            var pair = new Pair(inputClass, outputClass);
            var mediator = handlers.get(pair);
            if (mediator == null) {
                OldLogger.info(String.format(Messages.MEDIATOR_NOT_REGISTERED, inputClass.getSimpleName()));
                return null;
            }
        try {
            return (Output) mediator.handle(input);
        } catch (Exception e) {
            OldLogger.error("Error while executing mediator " + mediator.getClass().getSimpleName(), e);
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
            OldLogger.info(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, inputClass, mediator1));
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
