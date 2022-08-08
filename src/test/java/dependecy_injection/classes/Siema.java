package dependecy_injection.classes;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;

public interface Siema
{
    default void say()
    {
        FluentLogger.log(this.getClass().getSimpleName());
    }
}