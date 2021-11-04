package jw.spigot_fluent_api.tasks;

public class FluentTasks
{
    public static FluentTaskTimer taskTimer(int speed,TaskAction task)
    {
        return new FluentTaskTimer(speed,task);
    }
}
