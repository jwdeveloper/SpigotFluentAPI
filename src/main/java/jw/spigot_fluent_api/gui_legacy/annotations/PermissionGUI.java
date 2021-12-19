package jw.spigot_fluent_api.gui_legacy.annotations;



import jw.spigot_fluent_api.gui_legacy.button.ButtonActionsEnum;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Repeatable(PermissionGUIContainer.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionGUI
{
    String value() default "";
    ButtonActionsEnum action() default ButtonActionsEnum.EMPTY;
}
