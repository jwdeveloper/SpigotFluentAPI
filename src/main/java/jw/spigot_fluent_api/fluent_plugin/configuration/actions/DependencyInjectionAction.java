package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.data.annotation.files.JsonFile;
import jw.spigot_fluent_api.data.annotation.files.YmlFile;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.function.Consumer;

public class DependencyInjectionAction implements ConfigAction {

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
        InjectionManager.instance();
        InjectionManager.registerAllFromPackage(fluentPlugin.getClass().getPackage());
        consumer.accept(InjectionManager.instance());

         /*for(var yml : InjectionManager.getInjectedTypes())
        {
            FluentPlugin.logSuccess(yml.getSimpleName());
        }*/

    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
