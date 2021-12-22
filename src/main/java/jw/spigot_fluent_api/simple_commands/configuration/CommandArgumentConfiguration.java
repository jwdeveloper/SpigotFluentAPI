package jw.spigot_fluent_api.simple_commands.configuration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CommandArgumentConfiguration
{

    public CommandArgumentConfiguration setValidation()
    {
        return this;
    }

    public CommandArgumentConfiguration setDisplayedList(Supplier<List<String>> supplier)
    {
        return this;
    }

    public CommandArgumentConfiguration setName(String name)
    {
        return this;
    }

    public CommandArgumentConfiguration setDescription(String description)
    {
        return this;
    }

    public CommandArgumentConfiguration setValidationMessage(String message)
    {
        return this;
    }

    public SimpleCommandConfiguration apply()
    {
        return new SimpleCommandConfiguration(null);
    }
}
