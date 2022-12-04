package jw.fluent.plugin.api;

import jw.fluent.api.files.api.SimpleFilesBuilder;
import jw.fluent.api.logger.api.SimpleLoggerBuilder;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.spigot.commands.FluentApiCommandBuilder;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public interface FluentApiBuilder
{
    public FluentApiCommandBuilder command();
    public FluentApiContainerBuilder container();
    public FluentApiBuilder useExtention(FluentApiExtention extention);
    public SimpleLoggerBuilder logger();
    public SimpleFilesBuilder files();
    public FluentConfig config();
    public FluentPermissionBuilder permissions();
    public JavaPlugin plugin();
}
