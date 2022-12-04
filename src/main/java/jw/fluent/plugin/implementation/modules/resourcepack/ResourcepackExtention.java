package jw.fluent.plugin.implementation.modules.resourcepack;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.api.spigot.events.FluentEvent;
import jw.fluent.plugin.implementation.config.ConfigProperty;
import jw.fluent.plugin.implementation.config.FluentConfig;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.Consumer;

public class ResourcepackExtention implements FluentApiExtention {

    private final Consumer<ResourcepackOptions> consumer;
    private final String commandName = "resourcepack";

    public ResourcepackExtention(Consumer<ResourcepackOptions> options) {
        this.consumer = options;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        var options = loadOptions(builder.config());
        if (options.isLoadOnJoin()) {
            FluentEvent.onEvent(PlayerJoinEvent.class, playerJoinEvent ->
            {
                playerJoinEvent.getPlayer().setResourcePack(options.getResourcepackUrl());
            });
        }
        builder.command()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(commandName, commandBuilder ->
                    {
                        commandBuilder.propertiesConfig(propertiesConfig ->
                                {
                                    propertiesConfig.setDescription("downloads plugin resourcepack");
                                    propertiesConfig.setUsageMessage("/"+builder.command().getName()+" "+commandName);
                                })
                                .eventsConfig(eventConfig ->
                                {
                                    eventConfig.onPlayerExecute(event ->
                                    {
                                        event.getPlayer().setResourcePack(options.getResourcepackUrl());
                                    });
                                });
                    });
                });
    }

    public static byte[] toSHA1()
    {
        var version = FluentApi.plugin().getDescription().getVersion();
        var bytes = version.getBytes();
        var res = new byte[20];
        for(var i=0;i<res.length;i++)
        {
            if(i < bytes.length-1)
            {
                res[i] = bytes[i];
            }
            else
            {
                res[i] = (byte)i;
            }
        }
        return bytes;
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }

    private ResourcepackOptions loadOptions(FluentConfig config) {
        var options = new ResourcepackOptions();
        consumer.accept(options);
        var customUrl = getCustomUrl(config, options);
        var loadOnJoin = getLoadOnJoin(config, options);

        options.setResourcepackUrl(customUrl);
        options.setLoadOnJoin(loadOnJoin);
        return options;
    }


    private String getCustomUrl(FluentConfig config, ResourcepackOptions options) {
        var property = new ConfigProperty<String>("plugin.resourcepack.url",
                options.getResourcepackUrl(),
                "If you need to replace default resourcepack with your custom one",
                "set this to link of you resourcepack",
                "! after plugin update make sure your custom resourcepack is compatible !"
        );
        return config.getOrCreate(property);
    }

    private boolean getLoadOnJoin(FluentConfig config, ResourcepackOptions options) {
        var property = new ConfigProperty<Boolean>("plugin.resourcepack.load-on-join",
                options.isLoadOnJoin(),
                "Downloads resourcepack when player joins to server");
        return config.getOrCreate(property);
    }


}