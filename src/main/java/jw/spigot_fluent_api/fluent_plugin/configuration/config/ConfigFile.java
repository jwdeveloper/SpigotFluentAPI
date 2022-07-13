package jw.spigot_fluent_api.fluent_plugin.configuration.config;

public interface ConfigFile
{
    public Object getRequired(String name) throws Exception;

    public <T> T get(String name);

    public <T> T toObject(Class<T> clazz);
}
