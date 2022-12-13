package jw.fluent.api.utilites.code_generator;

import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

   /*
Example Input:
    %1Hello
    %2World

Example output:
     public List<String> generated()
    {
        var builder = new MessageBuilder();

        //line 1 %1Hello
        builder.color(ChatColor.AQUA).text("Hello").newLine();

        //line 1  %2World
        builder.color(ChatColor.AQUA).text("World").newLine();

        return List.of(builder.toArray());
    }

     */

public class SimpleMessageGenerator {
    public static void generate(String input, String output) throws IOException {
        List<String> fileContent = Files.readAllLines(Path.of(input));
        var result = createMethod(fileContent);
        var path = Path.of(output);
        Files.write(path, result.getBytes());
    }

    private static String createMethod(List<String> content) {
        var builder = new MessageBuilder();
        var methodOffset = 3;
        var contentOffset = 5;
        builder.newLine();
        builder.space(methodOffset).text("public List<String> generated()").newLine();
        builder.space(methodOffset).text("{").newLine();

        builder.space(contentOffset).text("var builder = new MessageBuilder();").newLine();
        builder.space(contentOffset).newLine();
        for (var i = 0; i < content.size(); i++) {
            var value = content.get(i);
            builder.space(contentOffset).text("//line ").text(i + 1).text(":").space().text(value).newLine();
            builder.space(contentOffset).text(createLineContent(value)).newLine();
            builder.space(contentOffset).newLine();
        }
        builder.space(contentOffset).newLine();
        builder.space(contentOffset).text("return List.of(builder.toArray());").newLine();
        builder.space(methodOffset).text("}").newLine();
        return builder.build();
    }


    private static String createLineContent(String line) {
        var subBuilder = new MessageBuilder();
        subBuilder.text("builder.");
        var split = line.split("\\%");
        for (var value : split) {
            if (value.length() == 0) {
                continue;
            }
            var colorIndex = value.charAt(0);
            var colorName = getColor(colorIndex);
            if(StringUtils.isNotNullOrEmpty(colorName))
            {
                subBuilder.text("color(").text(colorName).text(").").newLine();
            }

            if(value.length() == 1)
            {
                continue;
            }

            var text = value.substring(1);
            subBuilder.text("text(\"").text(text).text("\").").newLine();
        }
        subBuilder.text("newLine();");
        return subBuilder.toString();
    }

    private static String getColor(char value)
    {
        var color =  ChatColor.getByChar(value);
        if(color == null)
        {
            return StringUtils.EMPTY;
        }

        var base = "ChatColor.";
        var name = color.name().toUpperCase();
        return base+name;
    }


}
