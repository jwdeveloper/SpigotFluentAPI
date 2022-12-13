package jw.fluent.api.spigot.gui.fluent_ui.styles;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.gui.fluent_ui.styles.renderer.ButtonStyleRenderer;
import jw.fluent.api.spigot.gui.fluent_ui.styles.renderer.CatchButtonStyleRenderer;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import org.bukkit.ChatColor;

@Injection
public class FluentButtonStyle
{
    private final ButtonColorSet colorSet;
    private final ButtonStyleRenderer renderer;

    @Inject
    public FluentButtonStyle(FluentTranslator translator)
    {
        this.colorSet = getColorSet();
        this.renderer = new CatchButtonStyleRenderer(translator,colorSet);
    }

    public ButtonColorSet getColorSet()
    {
        var colorSet = new ButtonColorSet();
        colorSet.setPrimary(ChatColor.AQUA);
        colorSet.setSecondary(ChatColor.DARK_AQUA);
        colorSet.setTextBight("#C6C6C6");
        colorSet.setTextDark("#C6C6C6");
        return colorSet;
    }

}
