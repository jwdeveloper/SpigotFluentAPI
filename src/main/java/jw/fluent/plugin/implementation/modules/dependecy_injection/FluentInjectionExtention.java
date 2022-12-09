package jw.fluent.plugin.implementation.modules.dependecy_injection;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.IgnoreInjection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.ContainerConfiguration;
import jw.fluent.plugin.implementation.assembly_scanner.AssemblyScanner;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.FluentApiContainerBuilderImpl;

import java.util.ArrayList;
import java.util.List;

public class FluentInjectionExtention implements FluentApiExtension {


    public record Result(FluentInjectionImpl fluentInjection, List<Class<?>> toInitializeTypes){}
    private final List<Class<?>> toInitializeTypes;
    private final FluentApiContainerBuilderImpl builder;
    private final AssemblyScanner typeManager;

    private FluentInjectionImpl fluentInjection;

    public FluentInjectionExtention(FluentApiContainerBuilderImpl builder,
                                    AssemblyScanner classTypesManager)
    {
        this.builder = builder;
        toInitializeTypes = new ArrayList<>();
        this.typeManager = classTypesManager;
    }

    public Result create() throws Exception {

        var containerConfig = builder.getConfiguration();
        if(containerConfig.isEnableAutoScan())
        {
            autoScan(containerConfig);
        }

        var container = builder.build();
        fluentInjection = new FluentInjectionImpl(container);
        return new Result(fluentInjection, toInitializeTypes);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        for(var toActivate : toInitializeTypes)
        {
            fluentInjection.findInjection(toActivate);
        }
    }

    private void autoScan(ContainerConfiguration configuration)
    {
        var registeredClasses = configuration.getRegisterdTypes();
        var injectionsInfo = typeManager.findByAnnotation(Injection.class);
        for (var _class : injectionsInfo) {
            if (registeredClasses.contains(_class) ||
                    _class.isAnnotationPresent(IgnoreInjection.class) ||
                    _class.isInterface()) {
                continue;
            }

            registerType(_class);
        }
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

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }

}
