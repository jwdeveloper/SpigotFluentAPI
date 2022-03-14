package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.desing_patterns.mediator.Mediator;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

public class MediatorAction implements PluginPipeline
{
    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception
    {
       var mediators= FluentInjection
                                .getInjectionContainer()
                                .getAllByInterface(Mediator.class);
        FluentMediator.registerAll(mediators);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {

    }
}
