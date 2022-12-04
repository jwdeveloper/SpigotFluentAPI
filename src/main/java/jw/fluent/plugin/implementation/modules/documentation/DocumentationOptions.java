package jw.fluent.plugin.implementation.modules.documentation;

import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentationOptions
{
    private String path;

    private boolean renderCommandsDocumentation =true;

    private boolean renderPermissionsDocumentation =true;

    @Accessors(prefix = "use")
    private boolean githubDocumentation =true;

    @Accessors(prefix = "use")
    private boolean spigotDocumentation =true;

    private Class<?> permissionModel;

    private List<DocumentationDecorator> decorators = new ArrayList<>();

    public void addDecorator(DocumentationDecorator decorator)
    {
        decorators.add(decorator);
    }
}
