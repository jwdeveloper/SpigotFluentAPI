package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.languages.Lang;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
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
            var subPermissions = permission.split("\\.");
            for(var subPermission : subPermissions)
            {
                FluentLogger.log("sub permission ",subPermission);
                if (player.hasPermission(subPermission)) {
                    return true;
                }
            }

        }

        FluentMessage.message()
                .color(ChatColor.DARK_RED)
                .text(Lang.get("permissions.one-required")).send(player);

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

    public static boolean hasAllPermissions(Player player, String... permissions) {
        for (var permission : permissions) {
            if (!player.hasPermission(permission)) {
                FluentMessage.message()
                        .color(ChatColor.DARK_RED)
                        .text(Lang.get("permissions.all-required"))
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
