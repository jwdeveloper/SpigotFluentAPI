package jw.fluent.api.spigot.gui.fluent_ui.observers.enums;

import jw.fluent.api.spigot.gui.fluent_ui.observers.NotifierOptions;
import lombok.Setter;

import java.util.function.Function;

@Setter
public class EnumNotifierOptions extends NotifierOptions
{
    private Function<String,String> onNameMapping;
    public Function<String, String> getOnNameMapping() {
        return onNameMapping;
    }
}
