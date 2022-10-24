package jw.fluent_plugin.implementation.config;
import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.spigot.messages.FluentMessage;
import org.bukkit.configuration.file.FileConfiguration;
public record ConfigFileImpl(FileConfiguration fileConfiguration,
                             String path,
                             boolean updated,
                             boolean created) implements ConfigFile {

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
            OldLogger.error("Unable to save config file!",e);
        }

    }

    public Object getRequired(String name) throws Exception {
        var value = get(name);
        if (value == null) {
            throw new Exception("Value " + name + " is required");
        }
        return value;
    }

    @Override
    public <T> T getOrCreate(String path, T defaultValue, String ... description) {
        if(!fileConfiguration.contains(path))
        {
            fileConfiguration.set(path,defaultValue);
            var builder = FluentMessage.message();


            builder.text(fileConfiguration.options().header());
            builder.text(path);
            builder.newLine();
            for(var desc : description)
            {
                builder.bar(" ",3).text(desc).newLine();
            }
            fileConfiguration.options().header(builder.toString());
            save();
        }
        return (T)fileConfiguration.get(path);
    }

    @Override
    public <T> T getOrCreate(ConfigProperty<T> configProperty) {
        return getOrCreate(configProperty.path(),configProperty.defaultValue(),configProperty.description());
    }
}
