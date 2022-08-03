package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data;

import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
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