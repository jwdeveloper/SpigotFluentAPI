package jw.fluent.api.files.implementation.yml.api;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public interface YmlMapping extends ConfigurationSerializable
{
    public Object deserialize(Map<String ,Object> props);
}