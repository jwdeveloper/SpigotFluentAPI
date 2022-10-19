package jw.fluent_plugin.starup_actions.data;

import jw.fluent_api.minecraft.commands.api.builder.CommandBuilder;
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