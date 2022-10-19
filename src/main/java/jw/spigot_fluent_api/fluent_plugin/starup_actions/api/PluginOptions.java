package jw.spigot_fluent_api.fluent_plugin.starup_actions.api;

import org.bukkit.NamespacedKey;

public interface PluginOptions
{
    PluginOptions useUpdate(String url);

    PluginOptions useResourcePack(String url);

    PluginOptions useMetrics(Integer metrics);

    PluginOptions useDefaultLang(String lang);

    PluginOptions useDefaultNamespace(String namespace);

    PluginOptions useDefaultNamespace(NamespacedKey namespace);

    PluginOptions useCommand(String namespace);
}
