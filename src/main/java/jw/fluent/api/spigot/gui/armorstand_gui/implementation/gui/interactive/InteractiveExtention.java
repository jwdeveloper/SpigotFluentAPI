package jw.fluent.api.spigot.gui.armorstand_gui.implementation.gui.interactive;

import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class InteractiveExtention implements FluentApiExtension {


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.defaultCommand().subCommandsConfig(subCommandConfig->
        {
            var gui = new InteractiveGui();
            subCommandConfig.addSubCommand("open", commandBuilder ->
            {
                commandBuilder.eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(playerCommandEvent ->
                    {

                        gui.open(playerCommandEvent.getPlayer());
                    });
                });
            });
            subCommandConfig.addSubCommand("close", commandBuilder ->
            {
                commandBuilder.eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(playerCommandEvent ->
                    {

                        gui.close();
                    });
                });
            });
        });

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {




    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
