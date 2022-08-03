package jw.spigot_fluent_api.fluent_plugin.config;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import org.bukkit.configuration.file.FileConfiguration;
public record ConfigFileImpl(FileConfiguration fileConfiguration, String path,  boolean updated, boolean created) implements ConfigFile {

    public <T> T get(String name) {
        return (T) fileConfiguration.get(name);
    }

    public FileConfiguration config()
    {
        return fileConfiguration;
    }


    @Override
    public <T> T toObject(Class<T> clazz)
    {
        return null;
    }

    @Override
    public void save() {
        try
        {
            fileConfiguration.save(path);
        }
        catch (Exception e)
        {
            FluentLogger.error("Unable to save config file!",e);
        }

    }

    public Object getRequired(String name) throws Exception {
        var value = get(name);
        if (value == null) {
            throw new Exception("Value " + name + " is required");
        }
        return value;
    }
}
