package jw.spigot_fluent_api.fluent_plugin.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigFile
{
    public Object getRequired(String name) throws Exception;

    public <T> T get(String name);

    public <T> T toObject(Class<T> clazz);

    public void save();

    public FileConfiguration config();

    public boolean created();

    public boolean updated();
}
