package jw.fluent_plugin.implementation.modules.permissions.api;

import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_plugin.implementation.modules.permissions.implementation.DefaultPermissions;

public interface FluentPermissionBuilder
{
    public FluentPermissionBuilder registerPermission(PermissionModel model);

    public FluentPermissionBuilder setBasePermissionName(String name);

    public DefaultPermissions defaultPermissionSections();

    public String getBasePermissionName();

}
