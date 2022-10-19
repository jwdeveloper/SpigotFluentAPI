package jw.fluent_plugin.default_actions.implementation;

import jw.fluent_plugin.FluentPlugin;
import jw.fluent_plugin.default_actions.api.DefaultAction;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.fluent_api.utilites.metricts.MetricsLite;

public class MetricsAction extends DefaultAction {

    public MetricsAction(PluginOptionsImpl pluginOptions) {
        super(pluginOptions);
    }

    @Override
    public void pluginEnable(PipelineOptions options)
    {
        if(pluginOptions.getMetrics() == 0)
        {
            return;
        }

        new MetricsLite(options.getPlugin(),pluginOptions.getMetrics());
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
