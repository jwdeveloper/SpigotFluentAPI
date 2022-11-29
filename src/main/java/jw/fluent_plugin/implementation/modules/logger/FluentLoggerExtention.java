package jw.fluent_plugin.implementation.modules.logger;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.logger.implementation.SimpleLoggerBuilderImpl;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiContainerBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;

public class FluentLoggerExtention implements FluentApiExtention {

    private final SimpleLoggerBuilderImpl builder;
    public FluentLoggerExtention(SimpleLoggerBuilderImpl builder)
    {
        this.builder = builder;
    }



    @Override
    public void onConfiguration(FluentApiBuilder fluentApiBuilder) {
        var logger = builder.build();
        fluentApiBuilder.container().register(FluentLogger.class, LifeTime.SINGLETON,(x) -> new FluentLoggerImpl(logger));
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }
}
