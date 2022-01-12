package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTestsRunner;

public class IntegrationTestAction implements PluginPipeline
{

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) {
        SpigotIntegrationTestsRunner.loadTests();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
