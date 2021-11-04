package jw.spigot_fluent_api.utilites.binding;

public class BindingTemplate <T>
{
    protected T objectToBind;

    public BindingTemplate(T objectToBind)
    {
        this.objectToBind = objectToBind;
    }

}
