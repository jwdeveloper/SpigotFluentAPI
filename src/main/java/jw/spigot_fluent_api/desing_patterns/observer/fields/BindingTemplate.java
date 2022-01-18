package jw.spigot_fluent_api.desing_patterns.observer.fields;

public class BindingTemplate <T>
{
    protected T objectToBind;

    public BindingTemplate(T objectToBind)
    {
        this.objectToBind = objectToBind;
    }

}
