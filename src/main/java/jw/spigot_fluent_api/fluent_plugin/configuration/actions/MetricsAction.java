package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.metricts.MetricsLite;

public class MetricsAction implements ConfigAction {


    private int metricsKey =-1;
    public MetricsAction(int metricsKey)
    {
        this.metricsKey = metricsKey;
    }

    @Override
    public void execute(FluentPlugin fluentPlugin) throws Exception
    {
        new MetricsLite(fluentPlugin,metricsKey);
    }
}
