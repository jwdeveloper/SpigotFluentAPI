package jw.fluent_api.spigot.permissions.api;

import java.util.List;

public record PermissionGeneratorDto(Class<?> _class, String path, List<PermissionModel> permissionModels)
{

}
