package jw.fluent_plugin.implementation.modules.permissions.implementation;

import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_api.utilites.java.StringUtils;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class FluentPermissionBuilderImpl implements FluentPermissionBuilder {


    private final List<PermissionModel> models;
    private final JavaPlugin javaPlugin;

    private final DefaultPermissions defaultPermissionBuilder;
    private String basePermission;

    public FluentPermissionBuilderImpl(JavaPlugin javaPlugin)
    {
        models = new ArrayList<>();
        this.javaPlugin = javaPlugin;
        defaultPermissionBuilder = new DefaultPermissions();
    }

    @Override
    public FluentPermissionBuilder registerPermission(PermissionModel model) {
        models.add(model);
        return this;
    }

    @Override
    public FluentPermissionBuilder setBasePermissionName(String name) {
        basePermission= name;
        return this;
    }

    @Override
    public String getBasePermissionName() {

        if(StringUtils.nullOrEmpty(basePermission))
        {
            return javaPlugin.getName();
        }
        return basePermission;
    }

    public DefaultPermissions defaultPermissionSections()
    {
        return defaultPermissionBuilder;
    }


    public FluentPermissionImpl build()
    {
        var pluginPermission = defaultPermissionBuilder.plugin();
        pluginPermission.setName(getBasePermissionName());

        var command = defaultPermissionBuilder.commands();
        command.getGroups().add(pluginPermission.getParentGroup());

        var gui  = defaultPermissionBuilder.gui();
        gui.getGroups().add(pluginPermission.getParentGroup());

        models.add(pluginPermission);
        models.add(command);
        models.add(gui);
        return new FluentPermissionImpl(models, basePermission);
    }
}
