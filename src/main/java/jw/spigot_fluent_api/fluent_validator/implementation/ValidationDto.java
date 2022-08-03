package jw.spigot_fluent_api.fluent_validator.implementation;
import jw.spigot_fluent_api.fluent_validator.api.ValidationAction;
import lombok.Getter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Getter
public class ValidationDto
{
    private final HashMap<Field, List<ValidationAction>> actionList = new HashMap<>();


    public void addAction(Field field, ValidationAction validationAction)
    {
       var actions = actionList.get(field);
       if(actions == null)
       {
           actions = new ArrayList<>();
           actions.add(validationAction);
           actionList.put(field,actions);
       }
       else
       {
           actions.add(validationAction);
       }
    }
}
