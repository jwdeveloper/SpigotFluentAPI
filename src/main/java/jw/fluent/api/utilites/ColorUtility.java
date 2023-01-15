package jw.fluent.api.utilites;

import jw.fluent.api.utilites.java.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtility {
    public static Color fromHex(String hex) {
        if (StringUtils.isNullOrEmpty(hex)) {
            hex = "#FFFFFF";
        }
        if(hex.length() != 7)
        {
            hex = "#FFFFFF";
        }
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return Color.fromRGB(r, g, b);
    }

    public static java.awt.Color fromHexJava(String hex) {
        if (StringUtils.isNullOrEmpty(hex)) {
            hex = "#FFFFFF";
        }
        if(hex.length() != 7)
        {
            hex = "#FFFFFF";
        }
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return new java.awt.Color(r,g,b);
    }

    public static String chatColorFromHex(String hex)
    {
        var HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");
        hex = "&"+hex;
        Matcher matcher = HEX_PATTERN.matcher(hex);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(buffer,  net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static java.awt.Color darken(java.awt.Color color, double fraction) {

        int red = (int) Math.round(Math.max(0, color.getRed() - 255 * fraction));
        int green = (int) Math.round(Math.max(0, color.getGreen() - 255 * fraction));
        int blue = (int) Math.round(Math.max(0, color.getBlue() - 255 * fraction));

        int alpha = color.getAlpha();

        return new java.awt.Color(red, green, blue, alpha);
    }

    public static java.awt.Color lighten(java.awt.Color color, double fraction) {

        int red = (int) Math.round(Math.min(255, color.getRed() + 255 * fraction));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * fraction));

        int alpha = color.getAlpha();

        return new java.awt.Color(red, green, blue, alpha);
    }

    public static List<java.awt.Color> getColorBands(
            java.awt.Color color,
            int bands,
            boolean darker) {

        List<java.awt.Color> colorBands = new ArrayList<>(bands);

        if(darker == false)
        {
            for (int index = bands; index > 0; index--)
                colorBands.add(lighten(color, (double) index / (double) bands));
        }
        else
        {
            for (int index = 0; index < bands; index++)
                colorBands.add(darken(color, (double) index / (double) bands));
        }
        return colorBands;
    }

    public static String toHex(int r, int g, int b)
    {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
