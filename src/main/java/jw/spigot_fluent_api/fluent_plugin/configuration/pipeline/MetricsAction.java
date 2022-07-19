package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.metricts.MetricsLite;

import java.util.function.Supplier;

public class MetricsAction extends InfoMessageAction {

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
    public void pluginEnable(FluentPlugin fluentPlugin)
    {
        if(supplier != null)
        {
            metricsKey = supplier.get();
        }
        new MetricsLite(fluentPlugin,metricsKey);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
