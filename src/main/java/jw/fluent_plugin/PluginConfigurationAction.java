package jw.fluent_plugin;

import jw.fluent_plugin.default_actions.api.DefaultAction;
import jw.fluent_plugin.default_actions.implementation.updates.SimpleUpdateAction;
import jw.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.fluent_plugin.default_actions.implementation.MetricsAction;
import jw.fluent_plugin.default_actions.implementation.ResourcepackAction;

import java.util.ArrayList;
import java.util.List;

public class PluginConfigurationAction implements PluginPipeline {

    private List<DefaultAction> defaultActionList;

    public PluginConfigurationAction(PluginOptionsImpl pluginOptions)
    {
        defaultActionList= new ArrayList<>();
        defaultActionList.add(new MetricsAction(pluginOptions));
        defaultActionList.add(new SimpleUpdateAction(pluginOptions));
        defaultActionList.add(new ResourcepackAction(pluginOptions));
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception
    {
        for(var action : defaultActionList)
        {
            action.pluginEnable(options);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        for(var action : defaultActionList)
        {
            action.pluginDisable(fluentPlugin);
        }
    }
}
