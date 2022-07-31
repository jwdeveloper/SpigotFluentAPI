package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder.DependecyInjectionContainerBuilder;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;

import java.util.ArrayList;

public class DependecyInjectionAction implements PluginPipeline {

    private final DependecyInjectionContainerBuilder builder;

    public DependecyInjectionAction(DependecyInjectionContainerBuilder dependecyInjectionContainerBuilder) {
        this.builder = dependecyInjectionContainerBuilder;
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception
    {
        FluentInjection.setInjectionContainer(builder.build());
        if(!builder.isAutoRegistration())
            return;

       var _classes = options.getPlugin().getTypeManager().getByAnnotation(Injection.class);
       var registeredClasses = builder.getRegistered();
       var toInitializeClasses = new ArrayList<Class<?>>();
       for(var _class : _classes)
       {
           if(registeredClasses.contains(_class))
               continue;

           var injection = _class.getAnnotation(Injection.class);
           builder.register(_class,injection.lifeTime());

           if(!injection.lazyLoad())
               toInitializeClasses.add(_class);
       }

       for(var toInitialize : toInitializeClasses)
       {
           FluentInjection.getInjection(toInitialize);
       }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
