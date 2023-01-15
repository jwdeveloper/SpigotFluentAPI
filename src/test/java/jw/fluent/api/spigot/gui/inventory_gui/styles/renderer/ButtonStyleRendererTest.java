package jw.fluent.api.spigot.gui.inventory_gui.styles.renderer;

import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonColorSet;
import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonStyleInfo;
import jw.fluent.api.spigot.gui.fluent_ui.styles.renderer.ButtonStyleRenderer;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.FluentApiMock;
import jw.fluent.plugin.implementation.FluentApiBuilder;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


import static org.mockito.Mockito.mock;

public class ButtonStyleRendererTest {

    @Before
    public void setup()
    {
        FluentApiMock.getInstance();
    }

    @Test
    public void createDescription()
    {
        FluentApiBuilder.create((JavaPlugin) FluentApiMock.getInstance().getPluginMock()).build();
        // injection = (FluentInjectionImpl)FluentApi.container();

        var languageMock = mock(FluentTranslator.class);
        var colorSet = new ButtonColorSet();
        colorSet.setSecondary(ChatColor.AQUA);
        colorSet.setTextDark(ChatColor.DARK_AQUA);
        colorSet.setTextBight(ChatColor.WHITE);
        var sut = new ButtonStyleRenderer(languageMock, colorSet);


        var info = new ButtonStyleInfo();
        var stringBuilder = new MessageBuilder();
        info.setDescriptionLines(List.of("Play Midi files on this piano. Files should be located in plugins/JW_Piano/midi","a tam mieszakają polacy"));
        var result = sut.render(stringBuilder, info);


        Assert.assertTrue(result);
        System.out.println(stringBuilder.toString());
    }
}