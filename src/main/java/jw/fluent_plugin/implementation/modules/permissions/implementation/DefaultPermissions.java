package jw.fluent_plugin.implementation.modules.permissions.implementation;

import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

public class DefaultPermissions
{
    private PermissionModel commands;
    private PermissionModel gui;
    private PermissionModel plugin;

    public DefaultPermissions()
    {
        plugin = createDefaultModel("plugin");
        commands = createDefaultModel("commands");
        gui = createDefaultModel("gui");
    }


    public PermissionModel commands()
    {
        return commands;
    }

    public PermissionModel gui()
    {
        return gui;
    }

    public PermissionModel plugin()
    {
        return plugin;
    }


    private PermissionModel createDefaultModel(String name)
    {
        var permissionModel = new PermissionModel();
        permissionModel.setParent(true);
        permissionModel.setName(name);
        permissionModel.setTitle(name);
        permissionModel.setDescription("Default permission for "+name);
        permissionModel.setParentGroup(name);
        return permissionModel;
    }
}
