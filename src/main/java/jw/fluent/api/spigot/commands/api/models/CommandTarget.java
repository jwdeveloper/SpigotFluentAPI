package jw.fluent.api.spigot.commands.api.models;

import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandTarget
{
    private SimpleCommand simpleCommand;

    private String args[];
}
