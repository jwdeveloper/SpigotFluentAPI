package jw.spigot_fluent_api.tasks;


public interface TaskAction<T>
{
    void execute(int iteration, FluentTaskTimer task);
}
