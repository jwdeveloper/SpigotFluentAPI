package jw.fluent_plugin.implementation.modules.documentation;

import lombok.Data;

@Data
public class DocumentationOptions
{
    private String path;

    private boolean renderCommandsDocumentation =true;

    private boolean renderPermissionsDocumentation =true;

    private Class<?> permissionModel;
}
