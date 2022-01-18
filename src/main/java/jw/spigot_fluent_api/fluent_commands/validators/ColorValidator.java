package jw.spigot_fluent_api.fluent_commands.validators;
import org.bukkit.ChatColor;

public class ColorValidator extends EnumValidator<ChatColor>
{
    public ColorValidator() {
        super(ChatColor.class);
    }
}
