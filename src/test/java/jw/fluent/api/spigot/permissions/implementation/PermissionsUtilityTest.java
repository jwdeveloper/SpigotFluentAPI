package jw.fluent.api.spigot.permissions.implementation;

import jw.fluent.api.utilites.FluentApiMock;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class PermissionsUtilityTest {


    @Test
    void should_true_when_player_is_admin() {
        var player = FluentApiMock.getInstance().getPlayer();
        var permissions = getPermissions();
        player.setOp(true);
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertTrue(result);
    }


    @Test
    void should_false_pass_when_player_is_not_admin() {
        var player = FluentApiMock.getInstance().getPlayer();
        var permissions = getPermissions();
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertFalse(result);
    }


    @Test
    void should_false_pass_when_player_has_different_permissions() {
        var player = FluentApiMock.getInstance().getPlayer();
        var plugin = FluentApiMock.getInstance().getPluginMock();
        var permissions = getPermissions();


        var permission =player.addAttachment(plugin);
        permission.setPermission("anotherPlugin.*",true);
        permission.setPermission("plugin.commands.move",true);
        permission.setPermission("plugin.random",true);
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertFalse(result);
    }


    @Test
    void should_true_pass_when_player_has_exact_permissions() {
        var player = FluentApiMock.getInstance().getPlayer();
        var plugin = FluentApiMock.getInstance().getPluginMock();
        var permissions = getPermissions();


        var permission =player.addAttachment(plugin);
        permission.setPermission("plugin.commands.play",true);
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertTrue(result);
    }

    @Test
    void should_true_pass_when_player_has_group_permissions() {
        var player = FluentApiMock.getInstance().getPlayer();
        var plugin = FluentApiMock.getInstance().getPluginMock();
        var permissions = getPermissions();


        var permission =player.addAttachment(plugin);
        permission.setPermission("plugin.commands.*",true);
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertTrue(result);
    }

    @Test
    void should_true_pass_when_player_has_group_permissions_() {
        var player = FluentApiMock.getInstance().getPlayer();
        var plugin = FluentApiMock.getInstance().getPluginMock();
        var permissions = getPermissions();


        var permission =player.addAttachment(plugin);
        permission.setPermission("plugin.commands",true);
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertTrue(result);
    }

    @Test
    void should_true_pass_when_player_has_plugin_permissions() {
        var player = FluentApiMock.getInstance().getPlayer();
        var plugin = FluentApiMock.getInstance().getPluginMock();
        var permissions = getPermissions();


        var permission =player.addAttachment(plugin);
        permission.setPermission("plugin.*",true);
        var result = PermissionsUtility.hasOnePermission(player, permissions);
        Assert.assertTrue(result);
    }


    public List<String> getPermissions() {
        return List.of("plugin.*", "plugin.commands.*", "plugin.commands.play");
    }

}