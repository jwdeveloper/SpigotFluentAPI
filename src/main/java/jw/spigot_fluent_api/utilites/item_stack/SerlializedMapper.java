package jw.spigot_fluent_api.utilites.item_stack;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.apache.commons.lang.SerializationUtils;
import java.io.Serializable;

public class SerlializedMapper<T extends Serializable>
{
    public byte [] toBytes(T complex) {
        try
        {
            return SerializationUtils.serialize(complex);
        } catch (Exception e)
        {
            FluentLogger.error("Can not serialize "+complex.getClass().getSimpleName()+ "object to byte array",e);
        }
        return new byte[0];
    }

    public  T fromBytes(byte[] bytes)
    {
        try
        {
            return (T)SerializationUtils.deserialize(bytes);
        } catch (Exception e)
        {
            FluentLogger.error("Can not deserialize byte array to object",e);
        }
        return null;
    }
}
