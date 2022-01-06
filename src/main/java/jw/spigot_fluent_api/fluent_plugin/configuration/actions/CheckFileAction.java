package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;

public class CheckFileAction implements ConfigAction
{
    @Override
    public void execute(FluentPlugin fluentPlugin) throws Exception {
        if (!FileUtility.isPathValid(fluentPlugin.getPath() + ".jar"))
        {
            var msg = new MessageBuilder().color(ChatColor.YELLOW)
                    .text("Plugin can not be loaded since there is not ")
                    .color(ChatColor.RED)
                    .text(fluentPlugin.getName() + ".jar")
                    .color(ChatColor.YELLOW)
                    .text(" in plugins folder, check name of file").reset().toString();
            throw new Exception(msg);
        }
    }
}
