package jw.spigot_fluent_api.data.implementation.models;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.java.ObjectUtility;
import lombok.Data;
import org.bukkit.Material;

import java.util.Optional;
import java.util.UUID;

@Data
public abstract class DataModel {
    private UUID uuid = UUID.randomUUID();
    private String name = "";
    private String description = "";
    private Material icon = Material.DIRT;

    public boolean isNull() {
        return uuid == null;
    }


    public <T extends DataModel> Optional<T> copy() {
        try {

            var result = (T) ObjectUtility.copyObject(this, this.getClass());
            result.setUuid(getUuid());
            result.setName(getName());
            result.setDescription(getDescription());
            result.setIcon(getIcon());
            return Optional.of(result);
        } catch (Exception e) {
            FluentPlugin.logException("Can not copy object for class " + this.getClass().getSimpleName(), e);
        }
        return Optional.empty();
    }
}
