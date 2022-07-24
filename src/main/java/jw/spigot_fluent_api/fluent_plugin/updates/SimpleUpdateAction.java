package jw.spigot_fluent_api.fluent_plugin.updates;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.updates.api.data.UpdateDto;
import jw.spigot_fluent_api.fluent_plugin.updates.implementation.SimpleUpdater;
import org.bukkit.Bukkit;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleUpdateAction implements PluginPipeline {

    private Consumer<UpdateDto> consumer;

    public SimpleUpdateAction(Consumer<UpdateDto> consumer)
    {
         this.consumer = consumer;
    }

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        var dto = new UpdateDto();
       consumer.accept(dto);
        var autoUpdate = new SimpleUpdater(dto,fluentPlugin);
        FluentInjection.getInjectionContainer().register(autoUpdate);
        autoUpdate.checkUpdate(Bukkit.getConsoleSender());
    }




    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }



    public void checkVersion(FluentPlugin fluentPlugin) {

    }







    }
