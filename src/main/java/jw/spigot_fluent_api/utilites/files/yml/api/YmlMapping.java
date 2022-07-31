package jw.spigot_fluent_api.utilites.files.yml.api;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;

public interface YmlMapping extends ConfigurationSerializable
{
    public Object deserialize(Map<String ,Object> props);
}
