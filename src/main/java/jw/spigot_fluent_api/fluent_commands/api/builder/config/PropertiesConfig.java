package jw.spigot_fluent_api.fluent_commands.api.builder.config;

import jw.spigot_fluent_api.fluent_commands.api.builder.BuilderConfig;

public interface PropertiesConfig extends BuilderConfig {
    PropertiesConfig setUsageMessage(String usageMessage);

    PropertiesConfig setPermissionMessage(String permissionMessage);

    PropertiesConfig setLabel(String label);

    PropertiesConfig setShortDescription(String shortDescription);

    PropertiesConfig setDescription(String description);

    PropertiesConfig addOpPermission();

    PropertiesConfig addPermissions(String... permissions);
}
