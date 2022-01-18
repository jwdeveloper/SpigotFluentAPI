package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.function.Consumer;

public class DependencyInjectionAction implements PluginPipeline {

    private final Consumer<InjectionManager> consumer;

    public DependencyInjectionAction(Consumer<InjectionManager> consumer) {
        this.consumer = consumer;
    }

    public DependencyInjectionAction() {
        consumer = (a) -> {
        };
    }

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) {
      //  InjectionManager.registerAll(fluentPlugin.getTypeManager().getClasses());
      //  /consumer.accept(null);

         /*for(var yml : InjectionManager.getInjectedTypes())
        {
            FluentPlugin.logSuccess(yml.getSimpleName());
        }*/

    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
