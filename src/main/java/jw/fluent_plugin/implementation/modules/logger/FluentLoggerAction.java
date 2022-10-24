package jw.fluent_plugin.implementation.modules.logger;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.logger.implementation.SimpleLoggerImpl;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;

public class FluentLoggerAction implements PluginAction {
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {
        var loggerImpl = new SimpleLoggerImpl();
        var logger = new FluentLoggerImpl(loggerImpl);
        var registerInfo = new RegistrationInfo(FluentLogger.class,
                null,
                (x) -> {
                    return logger;
                },
                LifeTime.SINGLETON,
                RegistrationType.InterfaceAndProvider);
        var injection = (FluentInjectionImpl) FluentAPI.injection();
        injection.getContainer().register(registerInfo);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
