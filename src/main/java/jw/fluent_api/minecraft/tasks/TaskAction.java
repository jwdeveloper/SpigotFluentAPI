package jw.fluent_api.minecraft.tasks;


public interface TaskAction
{
    void execute(int iteration, FluentTaskTimer task);
}
