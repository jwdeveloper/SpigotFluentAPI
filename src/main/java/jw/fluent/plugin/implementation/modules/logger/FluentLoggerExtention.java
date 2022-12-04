package jw.fluent.plugin.implementation.modules.logger;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.logger.implementation.SimpleLoggerBuilderImpl;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;

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
