package jw.fluent.plugin.implementation.modules.metrics;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.api.utilites.metricts.MetricsLite;

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
