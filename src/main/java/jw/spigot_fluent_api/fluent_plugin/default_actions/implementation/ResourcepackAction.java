package jw.spigot_fluent_api.fluent_plugin.default_actions.implementation;

import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_events.FluentEvent;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigProperty;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PluginOptionsImpl;
import jw.spigot_fluent_api.fluent_plugin.default_actions.api.DefaultAction;
import jw.spigot_fluent_api.utilites.java.JavaUtils;
import org.bukkit.event.player.PlayerJoinEvent;

public class ResourcepackAction extends DefaultAction {

    public ResourcepackAction(PluginOptionsImpl pluginOptions) {
        super(pluginOptions);
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {
        if (!validate(pluginOptions.getResourcePack())) {
            return;
        }
        if (!validateCommand(options)) {
            return;
        }

        var url = pluginOptions.getResourcePack();
        var customResourcePack = getCustomUrl(options);
        if (validate(customResourcePack)) {
            url = customResourcePack;
        }
        command(options, url);

        var loadOnJoin = getOnJoin(options);
        if (loadOnJoin) {
            event(url);
        }
    }

    public void command(PipelineOptions pipelineOptions, String url) {
        pipelineOptions.getDefaultCommand()
                .getBuilder()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(FluentCommand
                            .create("resourcepack")
                            .eventsConfig(eventConfig ->
                            {
                                eventConfig.onPlayerExecute(event ->
                                {
                                    event.getPlayer().setResourcePack(url);
                                });
                            }));
                });
    }

    public void event(String url) {
        FluentEvent.onEvent(PlayerJoinEvent.class, playerJoinEvent ->
        {
            playerJoinEvent.getPlayer().setResourcePack(url);
        });
    }

    public String getCustomUrl(PipelineOptions options)
    {
        var property = new ConfigProperty<String>("plugin.resourcepack.custom-url",
                JavaUtils.EMPTY_STRING,
                "If you need to replace default resourcepack with your custom one",
                "set this to link of you resourcepack",
                "! after plugin update make sure your custom resourcepack is compatible !"
        );
        return options.getConfigFile().getOrCreate(property);
    }

    public boolean getOnJoin(PipelineOptions options) {
        var property = new ConfigProperty<Boolean>("plugin.resourcepack.load-on-join",
                true,
                "Downloads resourcepack when player joins to server");
        return options.getConfigFile().getOrCreate(property);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
