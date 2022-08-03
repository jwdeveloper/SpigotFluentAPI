package jw.spigot_fluent_api.fluent_commands.api.models;

import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandTarget
{
    private SimpleCommand simpleCommand;

    private String args[];
}
