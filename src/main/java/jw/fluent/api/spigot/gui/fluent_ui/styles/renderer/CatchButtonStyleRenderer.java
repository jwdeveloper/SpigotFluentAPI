package jw.fluent.api.spigot.gui.fluent_ui.styles.renderer;

import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonColorSet;
import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonStyleInfo;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;

import java.util.HashMap;
import java.util.List;

public class CatchButtonStyleRenderer extends ButtonStyleRenderer{

    private HashMap<String, List<String>> cache;

    public CatchButtonStyleRenderer(FluentTranslator translator, ButtonColorSet buttonColorSet) {
        super(translator, buttonColorSet);
        cache = new HashMap<>();
    }

    @Override
    public List<String> render(ButtonStyleInfo info) {

        if (cache.containsKey(info.getId())) {
            return cache.get(info.getId());
        }
        var result =  super.render(info);
        if(info.hasId())
        {
            cache.put(info.getId(),result);
        }
        return result;
    }
}
