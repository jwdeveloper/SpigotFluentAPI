package jw.fluent.api.desing_patterns.decorator.implementation;

import jw.fluent.api.desing_patterns.decorator.api.DecoratorInstanceProvider;
import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent.api.desing_patterns.dependecy_injection.implementation.utilites.Messages;

import java.util.Map;

public class DecoratorInstanceProviderImpl implements DecoratorInstanceProvider
{
    @Override
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Object toSwap, Container container) throws Exception {
        if (info.getLifeTime() == LifeTime.SINGLETON && info.getInstnace() != null)
            return info.getInstnace();

        Object result = null;
        InjectionInfo handler = null;
        if (info.hasInjectedConstructor()) {
            var i = 0;
            for (var parameter : info.getConstructorTypes())
            {
                if (!injections.containsKey(parameter))
                {
                    throw new Exception(String.format(Messages.INJECTION_NOT_FOUND, parameter.getTypeName(), info.getInjectionKeyType()));
                }
                handler = injections.get(parameter);
                if(info.getInjectionKeyType().equals(parameter))
                {
                    info.getConstructorPayLoadTemp()[i] = toSwap;
                }
                else
                {
                    info.getConstructorPayLoadTemp()[i] = getInstance(handler, injections, toSwap, container);
                }

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
