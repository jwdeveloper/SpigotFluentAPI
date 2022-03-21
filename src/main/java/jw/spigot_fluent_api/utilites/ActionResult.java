package jw.spigot_fluent_api.utilites;

import lombok.Data;

@Data
public class ActionResult<T>
{
    private boolean success = true;
    private T payload;
    private String message;

    public ActionResult(T object)
    {
      this.payload = object;
    }

    public boolean isFailed()
    {
        return !isSuccess();
    }

    public ActionResult(T object, boolean success)
    {
        this(object);
        this.success = success;
    }

    public ActionResult(T object, boolean status,String message)
    {
        this(object, status);
        this.message = message;
    }

    public static <T> ActionResult<T> success()
    {
       return new ActionResult<>(null,true);
    }

    public static <T>  ActionResult<T> success(T payload)
    {
        return new ActionResult<>(payload,true);
    }

    public static <T>  ActionResult<T> success(T payload, String message)
    {
        return new ActionResult<>(payload,true,message);
    }

    public static <T>  ActionResult<T> failed()
    {
        return new ActionResult<>(null,false);
    }

    public static <T>  ActionResult<T> failed(String message)
    {
        return new ActionResult<>(null,false,message);
    }
}
