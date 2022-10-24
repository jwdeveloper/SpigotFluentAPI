package jw.fluent_api.spigot.gameobjects.api.models;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomModel
{
    private ItemStack itemStack;
    @Getter
    private final Material material;
    @Getter
    private final int customModelId;

    public CustomModel(Material material, int customModelId)
    {
        this.material = material;
        this.customModelId = customModelId;
    }


    public CustomModel setName(String name)
    {
        getItemStack();

        var meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return this;
    }


    public CustomModel addProperty(PersistentDataType type, NamespacedKey key, Object value)
    {
        getItemStack();

        var meta = itemStack.getItemMeta();
        var container = meta.getPersistentDataContainer();

        container.set(key, type,value);
        itemStack.setItemMeta(meta);
        return this;
    }



    public CustomModel setId(NamespacedKey name, String  id)
    {
        getItemStack();
        var meta = itemStack.getItemMeta();
        var container= meta.getPersistentDataContainer();
        container.set(name, PersistentDataType.STRING,id);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack getItemStack()
    {
        if(itemStack != null)
            return itemStack.clone();

        itemStack = new ItemStack(material,1);
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
