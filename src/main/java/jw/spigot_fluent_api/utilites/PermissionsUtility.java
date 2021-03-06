package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public class PermissionsUtility
{
    public static boolean playerHasPermissions(Player player, List<String> permissions)
    {
       return playerHasPermissions(player,permissions.toArray(new String[permissions.size()]));
    }

    public static boolean playerHasPermissions(Player player, String[] permissions)
    {
        for (var permission: permissions)
        {
            if(!player.hasPermission(permission))
            {
                return false;
            }
        }
        return true;
    }

    public static void givePermission(Player player, String permission)
    {
        var attachment = player.addAttachment(FluentPlugin.getPlugin());
        attachment.setPermission(permission,true);
    }

    public static void removePermission(Player player, String permission)
    {
        player.getEffectivePermissions().forEach(permissionAttachmentInfo ->
        {
            permissionAttachmentInfo.getAttachment().getPermissions();
        });
    }

    public static Object[] getPermissions(Player player)
    {
        return  player.getEffectivePermissions().stream().map(PermissionAttachmentInfo::getPermission).toArray();
    }

}
