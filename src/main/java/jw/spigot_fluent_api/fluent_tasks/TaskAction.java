package jw.spigot_fluent_api.fluent_tasks;


public interface TaskAction
{
    void execute(int iteration, FluentTaskTimer task);
}
