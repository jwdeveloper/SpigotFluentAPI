package jw.fluent_plugin.implementation.modules.dependecy_injection;

import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.IgnoreInjection;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent_api.utilites.ClassTypesManager;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.FluentApiContainerBuilderImpl;

import java.util.ArrayList;
import java.util.List;

public class FluentInjectionFactory implements FluentApiExtention {
    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        for(var toActivate : toInitializeTypes)
        {
            fluentInjection.findInjection(toActivate);
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }

    public record Result(FluentInjectionImpl fluentInjection, List<Class<?>> toInitializeTypes){}
    private final List<Class<?>> toInitializeTypes;
    private final FluentApiContainerBuilderImpl builder;
    private final ClassTypesManager typeManager;

    private FluentInjectionImpl fluentInjection;

    public FluentInjectionFactory(FluentApiContainerBuilderImpl builder,
                                  ClassTypesManager classTypesManager)
    {
        this.builder = builder;
        toInitializeTypes = new ArrayList<>();
        this.typeManager = classTypesManager;
    }

    public Result create() throws Exception {

        var containerConfig = builder.getConfiguration();
        var registeredClasses = containerConfig.getRegisterdTypes();
        var injectionsInfo = typeManager.findByAnnotation(Injection.class);
        for (var _class : injectionsInfo) {
            if (registeredClasses.contains(_class) ||
                    _class.isAnnotationPresent(IgnoreInjection.class) ||
                    _class.isInterface()) {
                continue;
            }

            registerType(_class);
        }

        var container = builder.buildFluentContainer();
        fluentInjection = new FluentInjectionImpl(container);
        return new Result(fluentInjection, toInitializeTypes);
    }

    private void registerType(Class<?> _class) {
        var injectionInfo = _class.getAnnotation(Injection.class);
        if (!injectionInfo.lazyLoad())
            toInitializeTypes.add(_class);

        var interfaces = _class.getInterfaces();
        if (interfaces.length == 0 || injectionInfo.ignoreInterface()) {
            builder.register(_class, injectionInfo.lifeTime());
            return;
        }

        if (injectionInfo.toInterface().equals(Object.class)) {
            builder.register((Class) interfaces[0], _class, injectionInfo.lifeTime());
            return;
        }

        builder.register((Class) injectionInfo.toInterface(), _class, injectionInfo.lifeTime());
    }
}
