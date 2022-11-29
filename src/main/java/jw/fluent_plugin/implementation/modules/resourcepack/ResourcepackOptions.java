package jw.fluent_plugin.implementation.modules.resourcepack;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResourcepackOptions
{
    private String name;

    private String resourcepackUrl;

    private boolean loadOnJoin;
}
