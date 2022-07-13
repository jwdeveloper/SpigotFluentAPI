package jw.spigot_fluent_api.fluent_plugin.configuration.config;

import org.bukkit.configuration.file.FileConfiguration;

public record ConfigFileImpl(FileConfiguration fileConfiguration) implements ConfigFile {

    public <T> T get(String name) {
        return (T) fileConfiguration.get(name);
    }

    @Override
    public <T> T toObject(Class<T> clazz)
    {
        return null;
    }

    public Object getRequired(String name) throws Exception {
        var value = get(name);
        if (value == null) {
            throw new Exception("Value " + name + " is required");
        }
        return value;
    }
}
