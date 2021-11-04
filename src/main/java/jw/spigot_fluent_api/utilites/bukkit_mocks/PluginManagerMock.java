package jw.spigot_fluent_api.utilites.bukkit_mocks;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.*;

import java.io.File;
import java.util.Set;

public class PluginManagerMock implements PluginManager
{

    @Override
    public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException {

    }

    @Override
    public Plugin getPlugin(String name) {
        return null;
    }

    @Override
    public Plugin[] getPlugins() {
        return new Plugin[0];
    }

    @Override
    public boolean isPluginEnabled(String name) {
        return false;
    }

    @Override
    public boolean isPluginEnabled(Plugin plugin) {
        return false;
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
        return null;
    }

    @Override
    public Plugin[] loadPlugins(File directory) {
        return new Plugin[0];
    }

    @Override
    public void disablePlugins() {

    }

    @Override
    public void clearPlugins() {

    }

    @Override
    public void callEvent(Event event) throws IllegalStateException {

    }

    @Override
    public void registerEvents(Listener listener, Plugin plugin) {

    }

    @Override
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {

    }

    @Override
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {

    }

    @Override
    public void enablePlugin(Plugin plugin) {

    }

    @Override
    public void disablePlugin(Plugin plugin) {

    }

    @Override
    public Permission getPermission(String name) {
        return null;
    }

    @Override
    public void addPermission(Permission perm) {

    }

    @Override
    public void removePermission(Permission perm) {

    }

    @Override
    public void removePermission(String name) {

    }

    @Override
    public Set<Permission> getDefaultPermissions(boolean op) {
        return null;
    }

    @Override
    public void recalculatePermissionDefaults(Permission perm) {

    }

    @Override
    public void subscribeToPermission(String permission, Permissible permissible) {

    }

    @Override
    public void unsubscribeFromPermission(String permission, Permissible permissible) {

    }

    @Override
    public Set<Permissible> getPermissionSubscriptions(String permission) {
        return null;
    }

    @Override
    public void subscribeToDefaultPerms(boolean op, Permissible permissible) {

    }

    @Override
    public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {

    }

    @Override
    public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
        return null;
    }

    @Override
    public Set<Permission> getPermissions() {
        return null;
    }

    @Override
    public boolean useTimings() {
        return false;
    }
}
