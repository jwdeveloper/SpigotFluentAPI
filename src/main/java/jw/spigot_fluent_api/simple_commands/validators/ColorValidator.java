package jw.spigot_fluent_api.simple_commands.validators;
import org.bukkit.ChatColor;

public class ColorValidator extends EnumValidator<ChatColor>
{
    public ColorValidator() {
        super(ChatColor.class);
    }
}
