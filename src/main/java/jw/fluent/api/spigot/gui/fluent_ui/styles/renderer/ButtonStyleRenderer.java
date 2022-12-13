package jw.fluent.api.spigot.gui.fluent_ui.styles.renderer;

import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonColorSet;
import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonStyleInfo;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ButtonStyleRenderer {
    private final ButtonColorSet colorSet;
    private final FluentTranslator translator;
    private String barTop;
    private String barMid;
    private String barBottom;
    private final int barLength = 20;
    public ButtonStyleRenderer(FluentTranslator translator, ButtonColorSet buttonColorSet) {
        this.colorSet = buttonColorSet;
        this.translator = translator;
    }

    public String createBarTop() {
        if (barTop == null) {
            barTop = FluentApi.messages()
                    .chat()
                    .color(colorSet.getPrimary())
                    .color(ChatColor.BOLD)
                    .bar(Emoticons.boldBar, barLength)
                    .newLine()
                    .build();
        }
        return barTop;
    }

    public String createMid() {
        if (barMid == null) {
            barMid = FluentApi.messages()
                    .chat()
                    .color(colorSet.getPrimary())
                    .color(ChatColor.BOLD)
                    .bar(Emoticons.upperBar, barLength)
                    .newLine()
                    .build();
        }
        return barMid;
    }

    public String createBottom() {
        if (barBottom == null) {
            barBottom = FluentApi.messages()
                    .chat()
                    .color(colorSet.getPrimary())
                    .color(ChatColor.BOLD)
                    .bar(Emoticons.lowerBar, barLength)
                    .newLine()
                    .build();
        }
        return barBottom;
    }

    public String createTitle(String title) {
        return FluentApi.messages()
                .chat()
                .color(colorSet.getSecondary())
                .color(ChatColor.BOLD)
                .text("[")
                .color(colorSet.getPrimary())
                .text(title)
                .color(colorSet.getSecondary())
                .color(ChatColor.BOLD)
                .text("]")
                .build();
    }

    public List<String> render(ButtonStyleInfo info) {

        var builder = FluentApi.messages().chat();
        createTitle(builder, info);
        createObserverPlaceholders(builder,info);
        var descResult = render(builder, info);
        var infoResult = createClickInfo(builder, info);
        if (descResult || infoResult) {
            builder.text(createBottom());
        }

        return new ArrayList<>(Arrays.asList(builder.toArray()));
    }

    public void createTitle(MessageBuilder builder, ButtonStyleInfo info) {
        if (StringUtils.isNotNullOrEmpty(info.getDescriptionTitle())) {

            if(info.hasClickInfo() || info.hasDescription())
            {
                var title = info.getDescriptionTitle();
                var lenght = title.length()/2;
                var offSet =2;
                var halfBar = barLength/2;
                var location = halfBar-(lenght+offSet);
                builder.space(1);
            }
            else
            {
                builder.space(1);
            }
            builder.text(createTitle(info.getDescriptionTitle()));
            if(!info.hasClickInfo() && !info.hasDescription())
            {
                builder.space(1).newLine();
                builder.text(" ").newLine();
            }
            else
            {
                builder.newLine();
            }
        }
    }

    public void createObserverPlaceholders(MessageBuilder builder, ButtonStyleInfo info) {
        if (!info.hasObserverPlaceholdes())
        {
           return;
        }
        builder.newLine();
        for(var observer : info.getObserverPlaceholder())
        {
            builder.text("<observer>").text(observer).newLine();
        }
        builder.newLine();
    }

    public boolean render(MessageBuilder builder, ButtonStyleInfo info) {

        if (info.getDescriptionLines().isEmpty())
        {
            return false;
        }

        builder.text(createBarTop());
        builder.newLine();
        var temp = StringUtils.EMPTY;
        var limit = barLength+17;
        for (var line : info.getDescriptionLines()) {
            var text = temp+line;
            if(text.length() > limit)
            {
               var a = StringUtils.EMPTY;
               for(var end = text.length()-1;end >0;end--)
               {
                   var char_ = text.charAt(end);
                   if(char_ == ' ' && end < limit)
                   {
                       text = text.substring(0,end);
                    break;
                   }
                   a = char_ + a;
               }
               temp = a+" ";
            }
            else
            {
                temp = StringUtils.EMPTY;
            }
            builder.text(" ").color(colorSet.getTextBight()).text(text);
            builder.newLine();
        }
        while (!Objects.equals(temp, StringUtils.EMPTY))
        {
            var text = temp;
            if(text.length() > limit)
            {
                var a = StringUtils.EMPTY;
                for(var end = text.length()-1;end >0;end--)
                {
                    var char_ = text.charAt(end);
                    if(char_ == ' ' && end < limit)
                    {
                        text = text.substring(0,end);
                        break;
                    }
                    a = char_ + a;
                }
                temp = a+" ";
            }
            else
            {
                temp = StringUtils.EMPTY;
            }
            builder.text(" ").color(colorSet.getTextBight()).text(text);
            builder.newLine();
        }
        return true;
    }



    public boolean createClickInfo(MessageBuilder builder, ButtonStyleInfo info) {
        if (!info.hasClickInfo()) {
            return false;
        }
        builder.newLine();
        builder.color(colorSet.getSecondary()).text(" > ")
                .color(colorSet.getSecondary()).color(ChatColor.ITALIC).text(translator.get("gui.base.click-info"))
                .color(colorSet.getSecondary()).text(" <").newLine();

        builder.text(createMid());
        if (StringUtils.isNotNullOrEmpty(info.getLeftClick())) {
            createClickInfoLine(builder, translator.get("gui.base.left-click"), info.getLeftClick());

        }

        if (StringUtils.isNotNullOrEmpty(info.getRightClick())) {
            builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.right-click"), info.getRightClick());

        }

        if (StringUtils.isNotNullOrEmpty(info.getShiftClick())) {
            builder.newLine();
            createClickInfoLine(builder, translator.get("gui.base.shift-click"), info.getShiftClick());
        }
        return true;
    }

    public void createClickInfoLine(MessageBuilder builder, String name, String value) {
        var tile = createTitle(name);
        builder.space(2)
                .text(tile)

                .space(1)
                .color(colorSet.getPrimary())
                .color(ChatColor.BOLD)
                .text(">")
                .space(1)
                .color(colorSet.getTextDark())
                .text(value)
                .newLine();
    }
}
