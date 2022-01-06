package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import jw.spigot_fluent_api.utilites.metricts.MetricsLite;
import org.bukkit.ChatColor;

public class InfoMessageAction implements ConfigAction
{
    @Override
    public void execute(FluentPlugin fluentPlugin) {
        sendInfo(fluentPlugin,"Status","Enabled");
        sendInfo(fluentPlugin,"Version",fluentPlugin.getDescription().getVersion());
        sendInfo(fluentPlugin,"Metric","Enabled");
    }

    public void sendInfo(FluentPlugin fluentPlugin, String name, String value)
    {
        var message = new MessageBuilder()
                .color(ChatColor.WHITE)
                .withEndfix(name," ->")
                .space()
                .color(ChatColor.GREEN)
                .inBrackets(value)
                .color(ChatColor.WHITE).toString();
        fluentPlugin.logSuccess(message);
    }
}
