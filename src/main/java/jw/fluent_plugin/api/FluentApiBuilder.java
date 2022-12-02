package jw.fluent_plugin.api;

import jw.fluent_api.files.api.SimpleFilesBuilder;
import jw.fluent_api.logger.api.SimpleLoggerBuilder;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_plugin.implementation.config.FluentConfig;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public interface FluentApiBuilder
{
    public CommandBuilder command();
    public FluentApiContainerBuilder container();
    public FluentApiBuilder useExtention(FluentApiExtention extention);
    public SimpleLoggerBuilder logger();
    public SimpleFilesBuilder files();
    public FluentConfig config();
    public FluentPermissionBuilder permissions();

    public JavaPlugin plugin();
}
