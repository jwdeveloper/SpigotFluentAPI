package jw.spigot_fluent_api.tasks;


public interface TaskAction
{
    void execute(int iteration, FluentTaskTimer task);
}
