package jw.spigot_fluent_api.legacy_gui.annotations;



import jw.spigot_fluent_api.legacy_gui.button.ButtonActionsEnum;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Repeatable(PermissionGUIContainer.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionGUI
{
    String value() default "";
    ButtonActionsEnum action() default ButtonActionsEnum.EMPTY;
}
