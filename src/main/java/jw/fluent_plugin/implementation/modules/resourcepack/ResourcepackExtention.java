package jw.fluent_plugin.implementation.modules.resourcepack;

import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_api.spigot.commands.FluentCommand;
import jw.fluent_api.spigot.events.FluentEvent;
import jw.fluent_plugin.implementation.config.ConfigProperty;
import jw.fluent_api.utilites.java.JavaUtils;
import jw.fluent_plugin.implementation.config.FluentConfig;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.Consumer;

public class ResourcepackExtention implements FluentApiExtention {

    private Consumer<ResourcepackOptions> options;
    private String commandName = "resourcepack";
    public ResourcepackExtention(Consumer<ResourcepackOptions> options)
    {
       this.options = options;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        var config = builder.config();
        var url = getCustomUrl(config);
        var downloadOnJoin = getOnJoin(config);
        if (downloadOnJoin) {
            FluentEvent.onEvent(PlayerJoinEvent.class, playerJoinEvent ->
            {
                playerJoinEvent.getPlayer().setResourcePack(url);
            });
        }
        builder.command()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(FluentCommand
                            .create(commandName)
                            .eventsConfig(eventConfig ->
                            {
                                eventConfig.onPlayerExecute(event ->
                                {
                                    event.getPlayer().setResourcePack(url);
                                });
                            }));
                });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }

    public String getCustomUrl(FluentConfig config)
    {
        var property = new ConfigProperty<String>("plugin.resourcepack.custom-url",
                JavaUtils.EMPTY_STRING,
                "If you need to replace default resourcepack with your custom one",
                "set this to link of you resourcepack",
                "! after plugin update make sure your custom resourcepack is compatible !"
        );
        return config.getOrCreate(property);
    }

    public boolean getOnJoin(FluentConfig config) {
        var property = new ConfigProperty<Boolean>("plugin.resourcepack.load-on-join",
                true,
                "Downloads resourcepack when player joins to server");
        return config.getOrCreate(property);
    }


}
