package jw.spigot_fluent_api.fluent_commands.models;

import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandTarget {
    private SimpleCommand simpleCommand;

    private String args[];
}
