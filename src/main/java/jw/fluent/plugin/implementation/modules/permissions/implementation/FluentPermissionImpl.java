package jw.fluent.plugin.implementation.modules.permissions.implementation;

import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;

import java.util.List;

public class FluentPermissionImpl  implements FluentPermission {

    private List<PermissionModel> models;

    private String basePermissionName;

    public FluentPermissionImpl(List<PermissionModel> models, String basePermissionName)
    {
        this.models = models;
        this.basePermissionName = basePermissionName;
    }



    @Override
    public List<PermissionModel> getPermissions() {
        return models;
    }


}
