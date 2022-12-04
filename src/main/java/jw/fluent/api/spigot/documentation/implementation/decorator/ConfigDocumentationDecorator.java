package jw.fluent.api.spigot.documentation.implementation.decorator;

import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.utilites.files.FileUtility;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigDocumentationDecorator extends DocumentationDecorator {


    @Override
    public void decorate(Documentation documentation) {
        addTitle("Configuration",documentation,"yml-title");
        addImage("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/configuration.png",documentation);
        addYml(loadConfig(),documentation);
    }

    public String loadConfig()
    {
        try
        {
            var path = FluentApi.path()+ FileUtility.separator()+"config.yml";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return content;
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("ConfigDocumentationDecorator",e);
            return StringUtils.EMPTY_STRING;
        }
    }
}
