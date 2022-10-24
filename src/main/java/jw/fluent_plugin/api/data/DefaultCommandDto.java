package jw.fluent_plugin.api.data;

import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Consumer;

@AllArgsConstructor
@Data
public class DefaultCommandDto
{
    private String name;

    private Consumer<CommandBuilder> consumer;
}