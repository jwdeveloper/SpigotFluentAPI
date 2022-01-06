package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTestsRunner;

public class IntegrationTestAction implements ConfigAction
{

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) {
        SpigotIntegrationTestsRunner.loadTests();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
