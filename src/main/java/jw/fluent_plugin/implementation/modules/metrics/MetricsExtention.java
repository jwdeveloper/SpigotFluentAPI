package jw.fluent_plugin.implementation.modules.metrics;

import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_api.utilites.metricts.MetricsLite;

public class MetricsExtention implements FluentApiExtention {

    private int metricsId;

    public MetricsExtention(int metricsId) {
        this.metricsId = metricsId;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

        if (metricsId == 0) {
            return;
        }

        var metrics = new MetricsLite(builder.plugin(), metricsId);
        builder.container().registerSigleton(MetricsLite.class, metrics);
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {


    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
