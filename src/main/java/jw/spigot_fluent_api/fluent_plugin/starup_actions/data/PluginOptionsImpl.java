package jw.spigot_fluent_api.fluent_plugin.starup_actions.data;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.api.PluginOptions;
import jw.spigot_fluent_api.utilites.java.JavaUtils;
import lombok.Getter;
import org.bukkit.NamespacedKey;

@Getter
public class PluginOptionsImpl implements PluginOptions {

    private String resourcePack = JavaUtils.EMPTY_STRING;

    private String namespace = JavaUtils.EMPTY_STRING;

    private String updateURL = JavaUtils.EMPTY_STRING;

    private String lang = "en";

    private Integer metrics = 0;

    private String command = JavaUtils.EMPTY_STRING;

    private String permission = JavaUtils.EMPTY_STRING;

    public String getCommandName()
    {
        if(!command.equals(JavaUtils.EMPTY_STRING))
        {
            return command;
        }
        if(!namespace.equals(JavaUtils.EMPTY_STRING))
        {
            return namespace;
        }
        return FluentPlugin.getPlugin().getName();
    }

    public String getPermissionName()
    {
        if(!permission.equals(JavaUtils.EMPTY_STRING))
        {
            return command;
        }
        if(!namespace.equals(JavaUtils.EMPTY_STRING))
        {
            return namespace;
        }
        return FluentPlugin.getPlugin().getName();
    }

    @Override
    public PluginOptions useUpdate(String url) {
        updateURL = url;
        return this;
    }

    @Override
    public PluginOptions useResourcePack(String url) {
        resourcePack = url;
        return this;
    }

    @Override
    public PluginOptions useMetrics(Integer metrics) {
        this.metrics = metrics;
        return this;
    }

    @Override
    public PluginOptions useDefaultLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public PluginOptions useDefaultNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    @Override
    public PluginOptions useDefaultNamespace(NamespacedKey namespace) {
        this.namespace = namespace.getKey();
        return this;
    }

    @Override
    public PluginOptions useCommand(String namespace) {
        this.command = namespace;
        return this;
    }
}
