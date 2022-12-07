package jw.fluent.plugin.implementation.modules.resourcepack;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResourcepackOptions
{
    private String name;

    private String defaultUrl;

    private boolean loadOnJoin;
}
