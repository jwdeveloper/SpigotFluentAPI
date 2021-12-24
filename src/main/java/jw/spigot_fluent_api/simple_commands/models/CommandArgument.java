package jw.spigot_fluent_api.simple_commands.models;

import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandArgument
{
    private String name;

    private int position;

    private CommandArgumentType type;

    private List<CommandArgumentValidator> validators = new ArrayList<>();

    private String description;

    private ChatColor color;

    public void addValidator(CommandArgumentValidator validator)
    {
        validators.add(validator);
    }
}
