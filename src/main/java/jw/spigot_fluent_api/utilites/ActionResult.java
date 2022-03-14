package jw.spigot_fluent_api.utilites;

import lombok.Data;

@Data
public class ActionResult<T>
{
    private boolean status = true;
    private T payload;

    public ActionResult(T object)
    {
      this.payload = object;
    }

    public ActionResult(T object, boolean status)
    {
        this(object);
        this.status = status;
    }
}
