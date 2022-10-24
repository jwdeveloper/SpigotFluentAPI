package jw.fluent_plugin.implementation.modules.dependecy_injection;

import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.IgnoreInjection;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;

import java.util.ArrayList;

public class FluentInjectionAction implements PluginAction {

    private final ContainerBuilderImpl builder;

    public FluentInjectionAction(ContainerBuilderImpl dependecyInjectionContainerBuilder) {
        this.builder = dependecyInjectionContainerBuilder;
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {
        var container = builder.buildSearch();
        var config = builder.getConfiguration();


        var implementation = new FluentInjectionImpl(container);


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
            implementation.findInjection(toInitialize);
        }


    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
