package jw.fluent.api.player_context.implementation;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent.api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.utilites.Messages;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class PlayerContextInstanceProvider implements InstanceProvider {

    private Container parentContainer;

    public PlayerContextInstanceProvider(Container parentContainer)
    {
        this.parentContainer = parentContainer;
    }

    @Override
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Container container) throws Exception
    {
        if (info.getLifeTime() == LifeTime.SINGLETON && info.getInstnace() != null)
            return info.getInstnace();

        Object result = null;
        InjectionInfo handler = null;
        Class<?> parameterClass = null;
        if (info.hasInjectedConstructor()) {
            var i = 0;
            for (var parameterType : info.getConstructorTypes())
            {
                parameterClass = parameterType;
                if(parameterClass.isAssignableFrom(List.class))
                {
                    var parameterizedType = (ParameterizedType)info.getInjectedConstructor().getGenericParameterTypes()[i];
                    parameterClass =  (Class<?>)parameterizedType.getActualTypeArguments()[0];
                }
                if (!injections.containsKey(parameterClass))
                {
                    var instance = parentContainer.find(parameterClass);
                    if(instance == null)
                    {
                        throw new Exception(String.format(Messages.INJECTION_NOT_FOUND, parameterClass.getTypeName(), info.getInjectionKeyType()));
                    }
                    info.getConstructorPayLoadTemp()[i] = instance;
                    i++;
                    continue;
                }
                handler = injections.get(parameterClass);
                info.getConstructorPayLoadTemp()[i] = getInstance(handler, injections, container);
                i++;
            }
            result = info.getInjectedConstructor().newInstance(info.getConstructorPayLoadTemp());
            info.setInstnace(result);
            return result;
        }

        result = switch (info.getRegistrationInfo().registrationType())
                {
                    case InterfaceAndIml, OnlyImpl -> info.getRegistrationInfo().implementation().newInstance();
                    case InterfaceAndProvider, List -> info.getRegistrationInfo().provider().apply(container);
                };
        info.setInstnace(result);
        return result;
    }
}
