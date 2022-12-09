package jw.fluent.plugin.implementation.modules.files.logger;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.logger.implementation.SimpleLoggerBuilderImpl;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class FluentLoggerExtention implements FluentApiExtension {

    private final SimpleLoggerBuilderImpl builder;
    public FluentLoggerExtention(SimpleLoggerBuilderImpl builder)
    {
        this.builder = builder;
    }



    @Override
    public void onConfiguration(FluentApiSpigotBuilder fluentApiBuilder) {
        var logger = builder.build();
        fluentApiBuilder.container().register(FluentLogger.class, LifeTime.SINGLETON,(x) -> new FluentLoggerImpl(logger));
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
