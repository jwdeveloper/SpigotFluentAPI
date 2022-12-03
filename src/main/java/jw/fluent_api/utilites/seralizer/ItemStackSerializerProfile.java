package jw.fluent_api.utilites.seralizer;

import java.util.HashMap;
import java.util.Map;

public interface ItemStackSerializerProfile<T>
{
    public void serialize(Map<String, Object> map, T instance);

    public T deserialize(Map<String, Object> map);
}
