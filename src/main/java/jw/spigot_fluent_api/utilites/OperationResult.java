package jw.spigot_fluent_api.utilites;

import lombok.Data;

@Data
public class OperationResult<T>
{
    private boolean status = true;
    private T payload;

    public OperationResult(T object)
    {
      this.payload = object;
    }

    public OperationResult(T object, boolean status)
    {
        this(object);
        this.status = status;
    }
}
