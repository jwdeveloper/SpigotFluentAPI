package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import org.bukkit.ChatColor;

public class InfoMessageAction implements PluginPipeline {
    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) {
        sendInfo(fluentPlugin, "Status", "Enabled");
        sendInfo(fluentPlugin, "Version", fluentPlugin.getDescription().getVersion());

    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }

    protected void sendInfo(FluentPlugin fluentPlugin, String name, String value) {
        new MessageBuilder()
                .color(ChatColor.WHITE)
                .withEndfix(name, " ->")
                .space()
                .color(ChatColor.GREEN)
                .inBrackets(value)
                .color(ChatColor.WHITE)
                .sendToConsole();
    }
}
