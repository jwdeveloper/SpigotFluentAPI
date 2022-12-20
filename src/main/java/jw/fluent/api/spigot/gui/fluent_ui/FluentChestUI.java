package jw.fluent.api.spigot.gui.fluent_ui;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.gui.fluent_ui.styles.FluentButtonStyle;
import jw.fluent.api.spigot.gui.fluent_ui.styles.renderer.CatchButtonStyleRenderer;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;

@Injection
public class FluentChestUI
{
    private final FluentTranslator translator;
    private final FluentButtonStyle style;
    private final CatchButtonStyleRenderer renderer;

    @Inject
    public FluentChestUI(FluentTranslator translator)
    {
        this.translator = translator;
        style = new FluentButtonStyle(translator);
        renderer = new CatchButtonStyleRenderer(translator, style.getColorSet());
    }

    public FluentTranslator lang()
    {
        return translator;
    }

    public FluentButtonUIBuilder buttonBuilder()
    {
        return new FluentButtonUIBuilder(translator,renderer);
    }

    public FluentButtonUIFactory buttonFactory()
    {
        return new FluentButtonUIFactory(translator, style, buttonBuilder());
    }
}
