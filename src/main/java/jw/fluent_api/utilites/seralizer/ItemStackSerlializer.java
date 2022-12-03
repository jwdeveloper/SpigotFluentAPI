package jw.fluent_api.utilites.seralizer;

import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import org.apache.commons.lang.SerializationUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ItemStackSerlializer {
    private final ItemStackSerializerProfile profile;

    public ItemStackSerlializer(ItemStackSerializerProfile profile) {
        this.profile = profile;
    }

    public void serialize(ItemStack itemStack, Object obj) throws IOException {
        var namespace = getNamespace(obj.getClass());

        var map = new HashMap<String, Object>();
        profile.serialize(map, obj);

        var byteOut = new ByteArrayOutputStream();
        var out = new ObjectOutputStream(byteOut);
        out.writeObject(map);
        var meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(namespace, PersistentDataType.BYTE_ARRAY, byteOut.toByteArray());
        itemStack.setItemMeta(meta);
    }


    public <T> T deserialize(ItemStack itemStack, Class<T> _class) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        var namespace = getNamespace(_class);
        var bytes = itemStack.getItemMeta()
                .getPersistentDataContainer()
                .get(namespace, PersistentDataType.BYTE_ARRAY);
        if (bytes == null) {
            return _class.newInstance();
        }

        var byteIn = new ByteArrayInputStream(bytes);
        var in = new ObjectInputStream(byteIn);
        Map<String, Object> hashmap = (Map<String, Object>) in.readObject();
        var instance = profile.deserialize(hashmap);
        return (T) instance;
    }

    private NamespacedKey getNamespace(Class _class) {
        var plugin = FluentApi.getPlugin();
        return new NamespacedKey(plugin, _class.getName());
    }
}
