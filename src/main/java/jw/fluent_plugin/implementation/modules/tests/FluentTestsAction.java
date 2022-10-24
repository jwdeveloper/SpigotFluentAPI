package jw.fluent_plugin.implementation.modules.tests;

import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_api_tests_smoke.api.SpigotIntegrationTestsRunner;
import jw.fluent_api_tests_smoke.api.SpigotTest;

public class FluentTestsAction implements PluginAction
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
