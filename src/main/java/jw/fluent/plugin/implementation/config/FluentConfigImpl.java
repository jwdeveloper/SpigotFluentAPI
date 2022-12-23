package jw.fluent.plugin.implementation.config;
import jw.fluent.plugin.api.config.ConfigProperty;
import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.configuration.file.FileConfiguration;
public record FluentConfigImpl(FileConfiguration fileConfiguration,
                               String path,
                               boolean updated,
                               boolean created) implements FluentConfig {

    public <T> T get(String name) {
        return (T) fileConfiguration.get(name);
    }

    public FileConfiguration configFile()
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
            FluentLogger.LOGGER.error("Unable to save config path!",e);
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
            builder.newLine();
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
