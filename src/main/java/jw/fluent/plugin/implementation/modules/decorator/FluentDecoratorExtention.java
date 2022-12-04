package jw.fluent.plugin.implementation.modules.decorator;

import jw.fluent.api.desing_patterns.decorator.api.Decorator;
import jw.fluent.api.desing_patterns.decorator.api.builder.DecoratorBuilder;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;

public class FluentDecoratorExtention implements FluentApiExtention {

    private DecoratorBuilder decoratorBuilder;

    public FluentDecoratorExtention(DecoratorBuilder decoratorBuilder)
    {
        this.decoratorBuilder = decoratorBuilder;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

        builder.container().configure(containerConfiguration ->
        {
            try
            {
                Decorator decorator = decoratorBuilder.build();
                containerConfiguration.onEvent(decorator);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Unable to register decorator ",e);
                return;
            }
        });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }
}
