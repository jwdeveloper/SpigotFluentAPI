package jw.fluent_api_tests_smoke;

import jw.fluent_plugin.api.PluginConfiguration;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.config.ConfigFile;

public class TestPlugin extends FluentPlugin
{
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile)
    {
        configuration.runSmokeTests();
    }

    @Override
    protected void OnFluentPluginEnable() {

    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
