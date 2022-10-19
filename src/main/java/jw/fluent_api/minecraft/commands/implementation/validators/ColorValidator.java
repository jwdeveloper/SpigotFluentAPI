package jw.fluent_api.minecraft.commands.implementation.validators;
import org.bukkit.ChatColor;

public class ColorValidator extends EnumValidator<ChatColor>
{
    public ColorValidator() {
        super(ChatColor.class);
    }
}
