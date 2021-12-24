package jw.spigot_fluent_api.simple_commands.models;

import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommandModel {

    private String name;

    private String shortDescription ="";

    private String description = "";

    private String usageMessage ="";

    private String permissionMessage ="";

    private String label ="";

    private List<CommandAccessType> commandAccesses = new ArrayList<>();

    private List<String> permissions = new ArrayList<>();

    private List<CommandArgument> arguments = new ArrayList<>();

    private boolean allParametersRequired =true;
}
