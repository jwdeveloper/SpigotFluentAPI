package jw.fluent_api.minecraft.commands.api.models;

import jw.fluent_api.minecraft.commands.implementation.SimpleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommandTarget
{
    private SimpleCommand simpleCommand;

    private String args[];
}
