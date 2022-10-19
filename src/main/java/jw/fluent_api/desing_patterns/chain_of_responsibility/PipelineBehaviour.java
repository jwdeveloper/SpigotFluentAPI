package jw.fluent_api.desing_patterns.chain_of_responsibility;

public interface PipelineBehaviour<Input,Output>
{
    public Output Handle(Input input, Output next);
}
