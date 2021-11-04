package jw.spigot_fluent_api.data_models.models;

import org.bukkit.Material;

import java.util.UUID;

public abstract class DataModel
{
    public String id= UUID.randomUUID().toString();
    public String name= "";
    public String description= "";
    public String content= "";
    public Material icon =Material.DIRT;

    public boolean isNull()
    {
        return  id.equalsIgnoreCase("-1") == true;
    }
}
