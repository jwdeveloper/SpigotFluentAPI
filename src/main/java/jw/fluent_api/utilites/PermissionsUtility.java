package jw.fluent_api.utilites;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.spigot.messages.FluentMessage;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_api.utilites.java.JavaUtils;
import jw.fluent_api.utilites.messages.Emoticons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public class PermissionsUtility {
    public static boolean playerHasPermissions(Player player, List<String> permissions) {
        return playerHasPermissions(player, permissions.toArray(new String[permissions.size()]));
    }



    public static boolean hasOnePermission(Player player, List<String> permissions)
    {
        return hasOnePermission(player,permissions.toArray(new String[0]));
    }

    public static boolean hasOnePermission(Player player, String... permissions) {
        if(player.isOp())
        {
            return true;
        }

        for (var permission : permissions) {
            OldLogger.log(permission);
            var subPermissions = permission.split("\\.");
            for(var subPermission : subPermissions)
            {
                if (player.hasPermission(subPermission)) {
                    return true;
                }
            }

        }

        FluentMessage.message()
                .color(ChatColor.DARK_RED)
                .text(FluentAPI.lang().get("permissions.one-required")).send(player);

        for (var permission : permissions) {
            FluentMessage.message()
                    .color(ChatColor.GRAY)
                    .text(Emoticons.arrowRight)
                    .space()
                    .color(ChatColor.RED)
                    .text(permission)
                    .send(player);
        }
        return false;
    }

    public static boolean hasOnePermission(Player player, String permissions) {
        if(player.isOp())
        {
            return true;
        }
        if(permissions == null || permissions.equals(JavaUtils.EMPTY_STRING))
        {
            return true;
        }

        OldLogger.log(permissions);
        var subPermissions = permissions.split("\\.");
        for(var subPermission : subPermissions)
        {
            if (player.hasPermission(subPermission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAllPermissions(Player player, String... permissions) {
        for (var permission : permissions) {
            if (!player.hasPermission(permission)) {
                FluentMessage.message()
                        .color(ChatColor.DARK_RED)
                        .text(FluentAPI.lang().get("permissions.all-required"))
                        .color(ChatColor.GRAY)
                        .text(Emoticons.arrowRight)
                        .space()
                        .color(ChatColor.RED)
                        .text(permission)
                        .send(player);
                return false;
            }
        }
        return true;
    }

    public static boolean playerHasPermissions(Player player, String[] permissions) {
        for (var permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    public static void givePermission(Player player, String permission) {
        var attachment = player.addAttachment(FluentPlugin.getPlugin());
        attachment.setPermission(permission, true);
    }

    public static void removePermission(Player player, String permission) {
        player.getEffectivePermissions().forEach(permissionAttachmentInfo ->
        {
            permissionAttachmentInfo.getAttachment().getPermissions();
        });
    }

    public static Object[] getPermissions(Player player) {
        return player.getEffectivePermissions().stream().map(PermissionAttachmentInfo::getPermission).toArray();
    }

}
