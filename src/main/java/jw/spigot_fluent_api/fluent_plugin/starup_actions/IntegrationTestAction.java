package jw.spigot_fluent_api.fluent_plugin.starup_actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.spigot_fluent_api_integration_tests.SpigotIntegrationTestsRunner;
import jw.spigot_fluent_api_integration_tests.SpigotTest;

public class IntegrationTestAction implements PluginPipeline
{

    @Override
    public void pluginEnable(PipelineOptions options)
    {
        var classes = options.getPlugin().getTypeManager().getByInterface(SpigotTest.class);
        SpigotIntegrationTestsRunner.loadTests(classes);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
