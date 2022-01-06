package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.metricts.MetricsLite;

public class MetricsAction extends InfoMessageAction {


    private int metricsKey =-1;
    public MetricsAction(int metricsKey)
    {
        this.metricsKey = metricsKey;
    }

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin)
    {
        sendInfo(fluentPlugin,"Metric","Enabled");
        new MetricsLite(fluentPlugin,metricsKey);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
