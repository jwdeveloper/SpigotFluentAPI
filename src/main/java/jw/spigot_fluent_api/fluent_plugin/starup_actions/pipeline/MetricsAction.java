package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.utilites.metricts.MetricsLite;

import java.util.function.Supplier;

public class MetricsAction implements PluginPipeline {

    private Supplier<Integer> supplier;
    private int metricsKey =-1;
    public MetricsAction(int metricsKey)
    {
        this.metricsKey = metricsKey;
    }

    public MetricsAction(Supplier<Integer> supplier)
    {
        this.supplier = supplier;
    }

    @Override
    public void pluginEnable(PipelineOptions options)
    {
        if(supplier != null)
        {
            metricsKey = supplier.get();
        }
        new MetricsLite(options.getPlugin(),metricsKey);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
