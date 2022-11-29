package jw.fluent_api.desing_patterns.mediator.implementation;

import jw.fluent_api.desing_patterns.mediator.api.Mediator;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_api.utilites.java.Pair;
import jw.fluent_plugin.implementation.FluentApi;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.function.Function;

public class SimpleMediator implements Mediator {
    private final HashMap<Pair, Class> handlers;
    private static final String MEDIATOR_CLASS_NAME = MediatorHandler.class.getTypeName();

    private Function<Class<?>,Object> serviceResolver;

    public SimpleMediator(Function<Class<?>,Object> serviceResolver)
    {
        handlers = new HashMap<>();
        this.serviceResolver = serviceResolver;
    }

    @Override
    public <Output> Output resolve(Object input, Class<Output> outputClass) {
        if (input == null)
            return null;

        var inputClass = input.getClass();
        var pair = new Pair<>(inputClass, outputClass);
        var mediatorClass = handlers.get(pair);
        var mediatorImpl = (MediatorHandler)serviceResolver.apply(mediatorClass);
        if (mediatorClass == null) {
            FluentApi.logger().info(String.format(Messages.MEDIATOR_NOT_REGISTERED, inputClass.getSimpleName()));
            return null;
        }
        try {
            return (Output) mediatorImpl.handle(input);
        } catch (Exception e) {
            FluentApi.logger().error("Error while executing mediator " + mediatorClass.getSimpleName(), e);
            return null;
        }
    }

    @Override
    public <T extends MediatorHandler<?, ?>> void register(Class<T> mediator) {
        ParameterizedType mediatorInterface = null;
        for (var _interface : mediator.getGenericInterfaces()) {
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
        if (handlers.containsKey(inputClass)) {
            var registerOutput = handlers.get(inputClass);
            FluentApi.logger().info(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, inputClass, registerOutput));
            return;
        }
        handlers.put(new Pair(inputClass,outputClass), mediator);
    }


    @Override
    public boolean containerClass(Class pair) {
        return handlers.containsKey(pair);
    }

    public HashMap<Pair, Class> getRegisteredTypes() {
        return handlers;
    }
}
