package jw.fluent.api.spigot.gui.fluent_ui.observers.ints;

import jw.fluent.api.spigot.gui.fluent_ui.observers.NotifierOptions;
import lombok.Getter;
import lombok.Setter;

@Setter
public class IntNotifierOptions extends NotifierOptions
{
    @Getter
    private int yield = 10;

    private int minimum = 0;

    private int maximum = 100;

    private String prefix;

    String getPrefix()
    {
        return prefix;
    }

    int getMinimum()
    {
        return minimum;
    }

    int getMaximum()
    {
        return maximum;
    }
}
