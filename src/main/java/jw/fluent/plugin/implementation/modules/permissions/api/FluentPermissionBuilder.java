package jw.fluent.plugin.implementation.modules.permissions.api;

import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.plugin.implementation.modules.permissions.implementation.DefaultPermissions;

public interface FluentPermissionBuilder
{
    public FluentPermissionBuilder registerPermission(PermissionModel model);

    public FluentPermissionBuilder setBasePermissionName(String name);

    public DefaultPermissions defaultPermissionSections();

    public String getBasePermissionName();

}
