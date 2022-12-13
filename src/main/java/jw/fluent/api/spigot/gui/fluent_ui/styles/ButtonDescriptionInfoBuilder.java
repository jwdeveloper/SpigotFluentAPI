package jw.fluent.api.spigot.gui.fluent_ui.styles;

import java.util.List;

public class ButtonDescriptionInfoBuilder
{
    private ButtonStyleInfo buttonStyleInfo;

    public ButtonDescriptionInfoBuilder()
    {
        this(new ButtonStyleInfo());
    }

    public ButtonDescriptionInfoBuilder(ButtonStyleInfo renderer)
    {
        buttonStyleInfo = new ButtonStyleInfo();
    }

    public ButtonDescriptionInfoBuilder setCatchId(String id)
    {
        buttonStyleInfo.setId(id);
        return this;
    }

    public ButtonDescriptionInfoBuilder setTitle(String title)
    {
        buttonStyleInfo.setDescriptionTitle(title);
        return this;
    }

    public ButtonDescriptionInfoBuilder setOnLeftClick(String description)
    {
        buttonStyleInfo.setLeftClick(description);
        return this;
    }

    public ButtonDescriptionInfoBuilder setOnRightClick(String description)
    {
        buttonStyleInfo.setRightClick(description);
        return this;
    }

    public ButtonDescriptionInfoBuilder setOnShiftClick(String description)
    {
        buttonStyleInfo.setShiftClick(description);
        return this;
    }

    public ButtonDescriptionInfoBuilder addDescriptionLine(String line)
    {
        buttonStyleInfo.getDescriptionLines().add(line);
        return this;
    }

    public ButtonDescriptionInfoBuilder addDescriptionLine(List<String> lines)
    {
        buttonStyleInfo.getDescriptionLines().addAll(lines);
        return this;
    }

    public ButtonDescriptionInfoBuilder addDescriptionLine(String ... lines)
    {
        buttonStyleInfo.getDescriptionLines().addAll(List.of(lines));
        return this;
    }

    public ButtonDescriptionInfoBuilder addObserverPlaceholder(String placeholder)
    {
        buttonStyleInfo.getObserverPlaceholder().add(placeholder);
        return this;
    }

    public ButtonStyleInfo build()
    {
       return buttonStyleInfo;
    }

}
