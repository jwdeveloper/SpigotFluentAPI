package jw.fluent.api.utilites.item_stack;

import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
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
            FluentLogger.LOGGER.error("Can not serialize "+complex.getClass().getSimpleName()+ "object to byte array",e);
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
            FluentLogger.LOGGER.error("Can not deserialize byte array to object",e);
        }
        return null;
    }
}
