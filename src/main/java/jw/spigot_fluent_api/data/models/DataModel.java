package jw.spigot_fluent_api.data.models;

import org.bukkit.Material;

import java.util.UUID;

public abstract class DataModel {
    public UUID uuid = UUID.randomUUID();
    public String name = "";
    public String description = "";
    public String content = "";
    public Material icon = Material.DIRT;

    public boolean isNull() {
        return uuid == null;
    }
}
