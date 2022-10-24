package jw.fluent_plugin.implementation.modules.plugin;

import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.default_actions.DefaultAction;
import jw.fluent_plugin.implementation.modules.updater.SimpleUpdaterAction;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
import jw.fluent_plugin.implementation.default_actions.MetricsAction;
import jw.fluent_plugin.implementation.default_actions.ResourcepackAction;

import java.util.ArrayList;
import java.util.List;

public class FluentPluginConfigurationAction implements PluginAction
{

    private List<DefaultAction> defaultActionList;

    public FluentPluginConfigurationAction(PluginOptionsImpl pluginOptions)
    {
        defaultActionList= new ArrayList<>();
        defaultActionList.add(new MetricsAction(pluginOptions));
        defaultActionList.add(new SimpleUpdaterAction(pluginOptions));
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
