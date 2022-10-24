package jw.fluent_plugin.implementation.default_actions;

import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
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
