package jw.spigot_fluent_api.fluent_plugin.starup_actions;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.IgnoreInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;

import java.util.ArrayList;

public class DependecyInjectionAction implements PluginPipeline {

    private final ContainerBuilderImpl builder;

    public DependecyInjectionAction(ContainerBuilderImpl dependecyInjectionContainerBuilder) {
        this.builder = dependecyInjectionContainerBuilder;
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {
        var container = builder.buildSearch();
        var config = builder.getConfiguration();
        FluentInjection.setContainer(container);

        var _classes = options.getPlugin().getTypeManager().getByAnnotation(Injection.class);
        var registeredClasses = config.getRegisterdTypes();
        var toInitializeClasses = new ArrayList<Class<?>>();
        for (var _class : _classes) {
            if (registeredClasses.contains(_class))
                continue;
            var injection = _class.getAnnotation(Injection.class);
            if (_class.isInterface()) {
                var subClasses = options.getPlugin().getTypeManager().getByInterface(_class);
                for (var subClass : subClasses) {
                    var isIgnored = subClass.getAnnotation(IgnoreInjection.class);
                    if(isIgnored != null)
                    {
                        continue;
                    }
                    if (registeredClasses.contains(subClass))
                        continue;
                    builder.register(subClass, injection.lifeTime());
                    if (!injection.lazyLoad())
                        toInitializeClasses.add(subClass);
                }
                continue;
            }

            builder.register(_class, injection.lifeTime());
            if (!injection.lazyLoad())
                toInitializeClasses.add(_class);
        }

        for (var toInitialize : toInitializeClasses) {
            FluentInjection.findInjection(toInitialize);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
