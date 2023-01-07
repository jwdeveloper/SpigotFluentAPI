package jw.fluent.api.utilites;

import jw.fluent.api.utilites.java.StringUtils;
import org.bukkit.Color;

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
}
