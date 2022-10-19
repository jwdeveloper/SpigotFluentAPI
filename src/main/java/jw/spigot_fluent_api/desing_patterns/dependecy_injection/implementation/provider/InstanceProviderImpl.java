package jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.provider;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.utilites.Messages;

import java.util.Map;

public class InstanceProviderImpl implements InstanceProvider
{
    @Override
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections) throws Exception {
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
                info.getConstructorPayLoadTemp()[i] = getInstance(handler, injections);
                i++;
            }
            result = info.getInjectedConstructor().newInstance(info.getConstructorPayLoadTemp());
            info.setInstnace(result);
            return result;
        }

        result = switch (info.getRegistrationInfo().registrationType())
        {
            case InterfaceAndIml, OnlyImpl -> info.getRegistrationInfo().implementation().newInstance();
            case InterfaceAndProvider -> info.getRegistrationInfo().provider().apply(injections);
        };
        info.setInstnace(result);
        return result;
    }
}
