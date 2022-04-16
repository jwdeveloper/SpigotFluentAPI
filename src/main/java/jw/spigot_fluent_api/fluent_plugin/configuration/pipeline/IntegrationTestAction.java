package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTestsRunner;
import jw.spigot_fluent_api_integration_tests.SpigotTest;

public class IntegrationTestAction implements PluginPipeline
{

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin)
    {
        var classes = fluentPlugin.getTypeManager().getByInterface(SpigotTest.class);
        SpigotIntegrationTestsRunner.loadTests(classes);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
