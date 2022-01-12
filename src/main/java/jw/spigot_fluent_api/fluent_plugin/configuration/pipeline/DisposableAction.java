package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.disposing.Disposable;

public class DisposableAction  implements PluginPipeline
{

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception
    {

    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
       InjectionManager.dispose();
    }
}
