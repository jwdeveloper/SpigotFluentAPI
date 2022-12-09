package jw.fluent.plugin.implementation.modules.metrics;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class MetricsExtention implements FluentApiExtension {

    private int metricsId;

    public MetricsExtention(int metricsId) {
        this.metricsId = metricsId;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        if (metricsId == 0) {
            return;
        }

        var metrics = new MetricsLite(builder.plugin(), metricsId);
        builder.container().registerSigleton(MetricsLite.class, metrics);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {


    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }
}
