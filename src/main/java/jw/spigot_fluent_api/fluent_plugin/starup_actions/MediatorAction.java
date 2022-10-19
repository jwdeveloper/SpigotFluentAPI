package jw.spigot_fluent_api.fluent_plugin.starup_actions;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;

public class MediatorAction implements PluginPipeline
{
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception
    {
       var mediators= FluentInjection
                                .getContainer()
                                .findAllByInterface(MediatorHandler.class);
       for(var mediator : mediators)
       {
           FluentMediator.register(mediator);
       }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {

    }
}
