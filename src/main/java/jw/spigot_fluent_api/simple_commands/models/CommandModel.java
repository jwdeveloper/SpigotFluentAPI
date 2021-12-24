package jw.spigot_fluent_api.simple_commands.models;

import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CommandModel {

    private String name;

    private String shortDescription;

    private String description;

    private List<CommandAccessType> commandAccesses;

    private List<String> permissions;

    private List<CommandArgument> arguments;
}
