package jw.fluent.api.spigot.permissions.implementation;

import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public class PermissionsUtility {



    public static boolean hasOnePermission(Player player, List<String> permissions)
    {
        return hasOnePermission(player,permissions.toArray(new String[0]));
    }

    public static boolean hasOnePermission(Player player, String... permissions) {
        if (player.isOp()) {
            return true;
        }
        if (permissions == null || permissions.length == 0) {
           return true;
        }
        var last = StringUtils.EMPTY_STRING;
        var current = StringUtils.EMPTY_STRING;
        var subPermissions = new String[0];
        for (var permission : permissions) {
            if (permission == null) {
                return true;
            }
            subPermissions = permission.split("\\.");
            last = StringUtils.EMPTY_STRING;
            current = StringUtils.EMPTY_STRING;
            for (var i = 0; i < subPermissions.length; i++) {
                if (last.equals(StringUtils.EMPTY_STRING)) {
                    current = subPermissions[i].replace(".", StringUtils.EMPTY_STRING);
                } else {
                    current = last + "." + subPermissions[i];
                }
                last = current;
                // FluentMessage.message().field("Permission",current).sendToConsole();
                if (player.hasPermission(current)) {
                    return true;
                }
            }
        }
        FluentMessage.message()
                .color(ChatColor.DARK_RED)
                .text(FluentApi.translator().get("permissions.one-required")).send(player);

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

    public static boolean hasOnePermissionWithoutMessage(Player player, String... permissions) {
        if (player.isOp()) {
            return true;
        }
        if (permissions == null || permissions.length == 0) {
            return true;
        }
        var last = StringUtils.EMPTY_STRING;
        var current = StringUtils.EMPTY_STRING;
        var subPermissions = new String[0];
        for (var permission : permissions) {
            if (permission == null) {
                return true;
            }
            subPermissions = permission.split("\\.");
            last = StringUtils.EMPTY_STRING;
            current = StringUtils.EMPTY_STRING;
            for (var i = 0; i < subPermissions.length; i++) {
                if (last.equals(StringUtils.EMPTY_STRING)) {
                    current = subPermissions[i].replace(".", StringUtils.EMPTY_STRING);
                } else {
                    current = last + "." + subPermissions[i];
                }
                last = current;
                // FluentMessage.message().field("Permission",current).sendToConsole();
                if (player.hasPermission(current)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasAllPermissions(Player player, String... permissions) {
        for (var permission : permissions) {
            if (!player.hasPermission(permission)) {
                FluentMessage.message()
                        .color(ChatColor.DARK_RED)
                        .text(FluentApi.translator().get("permissions.all-required"))
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
        var attachment = player.addAttachment(FluentApi.plugin());
        attachment.setPermission(permission, true);
    }

    public static void showPlayerPermissions(Player player) {
        var builder = FluentMessage.message();

        builder.newLine().bar(Emoticons.line,60).newLine();
        builder.inBrackets("Permissions",ChatColor.AQUA).newLine();
        builder.field("Player",player.getName()).newLine();
        player.getEffectivePermissions().stream().forEach(permissionAttachmentInfo ->
        {
             builder.field("X",permissionAttachmentInfo.getPermission()).newLine();
        });
        builder.reset().bar(Emoticons.line,60).newLine();
        builder.sendToConsole();
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
