package jw.fluent.api.files.implementation.json.adapters;


import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    private final Gson gson;
    public ItemStackAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        try
        {
            Map<String, Object> map = gson.fromJson(jsonElement.getAsString(), new TypeToken<Map<String, Object>>(){}.getType());
            return ItemStack.deserialize(map);
        }
        catch (JsonParseException e)
        {

        }
        return null;
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context)
    {
        return new JsonPrimitive(gson.toJson(itemStack.serialize()));
    }
}