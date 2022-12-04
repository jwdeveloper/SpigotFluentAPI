package integration.desing_patterns.dependecy_injection.example_classes;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import lombok.Getter;

import java.util.List;

@Injection
public class ExampleWithList
{
    @Getter
    private final List<ExampleInterface> exampleInterfaces;

    @Inject
    public ExampleWithList(List<ExampleInterface> exampleInterfaces) {
        this.exampleInterfaces = exampleInterfaces;
    }
}
