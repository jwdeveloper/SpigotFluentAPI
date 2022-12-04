package jw.fluent.api.utilites.item_stack;

import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;

public class ItemStackDataFormatter
{

    public static <T extends Serializable> T getData(JavaPlugin plugin,Class<T> _class, ItemStack itemStack)
    {
        try {
            var key = new NamespacedKey(plugin, _class.getSimpleName());
            var itemMeta = itemStack.getItemMeta();
            var tagContainer = itemMeta.getPersistentDataContainer();
            var bytes =  tagContainer.getOrDefault(key,PersistentDataType.BYTE_ARRAY,null);
            if(bytes == null)
                return null;
            var mapper = new SerlializedMapper<T>();
            return mapper.fromBytes(bytes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Serializable> T getData(Class<T> _class, ItemStack itemStack)
    {
        return getData(FluentApi.plugin(),_class,itemStack);
    }

    public static <T extends Serializable> boolean saveData(JavaPlugin javaPlugin, T _object, ItemStack itemStack)
    {
        try {
            var key = new NamespacedKey(javaPlugin, _object.getClass().getSimpleName());
            var itemMeta = itemStack.getItemMeta();
            var tagContainer = itemMeta.getPersistentDataContainer();
            if(tagContainer.has(key,PersistentDataType.BYTE_ARRAY))
            {
                tagContainer.remove(key);
            }

            var mapper = new SerlializedMapper<T>();
            var bytes = mapper.toBytes(_object);
            tagContainer.set(key,PersistentDataType.BYTE_ARRAY,bytes);
            itemStack.setItemMeta(itemMeta);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static <T extends Serializable> boolean saveData(T _object, ItemStack itemStack)
    {
        return saveData(FluentApi.plugin(), _object,  itemStack);
    }

    public static <T extends Serializable> boolean containsObject(JavaPlugin plugin,Class<T> _class, ItemStack itemStack)
    {
        var key = new NamespacedKey(plugin, _class.getClass().getSimpleName());
        var itemMeta = itemStack.getItemMeta();
        var tagContainer = itemMeta.getPersistentDataContainer();
        return tagContainer.has(key,PersistentDataType.BYTE_ARRAY);
    }

    public static <T extends Serializable> boolean containsObject(Class<T> _class, ItemStack itemStack)
    {
        return containsObject(FluentApi.plugin(),_class,itemStack);
    }
}
