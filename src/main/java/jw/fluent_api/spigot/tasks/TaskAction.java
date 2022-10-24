package jw.fluent_api.spigot.tasks;


public interface TaskAction
{
    void execute(int iteration, FluentTaskTimer task);
}
