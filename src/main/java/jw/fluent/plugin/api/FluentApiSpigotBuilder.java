package jw.fluent.plugin.api;

import jw.fluent.api.files.api.SimpleFilesBuilder;
import jw.fluent.api.logger.api.SimpleLoggerBuilder;
import jw.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.command.FluentApiCommandBuilder;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public interface FluentApiSpigotBuilder
{
     FluentApiCommandBuilder defaultCommand();
     FluentApiContainerBuilder container();
     FluentApiSpigotBuilder useExtension(FluentApiExtension extension);
     SimpleLoggerBuilder logger();
     SimpleFilesBuilder files();
     FluentConfig config();
     FluentPermissionBuilder permissions();
     JavaPlugin plugin();
    FluentAssemblyScanner assemblyScanner();
}
