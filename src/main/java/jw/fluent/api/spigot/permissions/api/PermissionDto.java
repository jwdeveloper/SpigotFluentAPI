package jw.fluent.api.spigot.permissions.api;

import java.util.List;

public record PermissionDto(Class<?> _class, List<PermissionModel> permissionModels)
{

}
