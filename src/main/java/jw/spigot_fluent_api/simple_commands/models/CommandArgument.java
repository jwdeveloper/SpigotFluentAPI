package jw.spigot_fluent_api.simple_commands.models;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.List;

@Getter
@Setter
public class CommandArgument
{
    private String name;

    private int position;

    private Class<?> type;

    private List<CommandArgumentValidator> validators;

    private String description;

    private ChatColor color;
}
