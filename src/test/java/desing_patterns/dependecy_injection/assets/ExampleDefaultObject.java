package desing_patterns.dependecy_injection.assets;

import lombok.Getter;

public class ExampleDefaultObject
{
    @Getter
    private String name;

    public ExampleDefaultObject(String name)
    {
         this.name = name;
    }
}
