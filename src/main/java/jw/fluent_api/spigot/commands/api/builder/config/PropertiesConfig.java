package jw.fluent_api.spigot.commands.api.builder.config;

import jw.fluent_api.spigot.commands.api.builder.BuilderConfig;

public interface PropertiesConfig extends BuilderConfig {
    PropertiesConfig setUsageMessage(String usageMessage);

    PropertiesConfig setPermissionMessage(String permissionMessage);

    PropertiesConfig setLabel(String label);

    PropertiesConfig setShortDescription(String shortDescription);

    PropertiesConfig setDescription(String description);


    PropertiesConfig addPermissions(String... permissions);
}
