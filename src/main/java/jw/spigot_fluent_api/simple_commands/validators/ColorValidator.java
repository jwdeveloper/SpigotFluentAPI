package jw.spigot_fluent_api.simple_commands.validators;

import jw.spigot_fluent_api.simple_commands.models.CommandArgumentValidator;
import org.bukkit.ChatColor;

public class ColorValidator implements CommandArgumentValidator {
    @Override
    public boolean validate(String arg) {
        try {
            ChatColor.valueOf(arg.toLowerCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
