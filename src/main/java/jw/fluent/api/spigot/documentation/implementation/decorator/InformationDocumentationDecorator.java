package jw.fluent.api.spigot.documentation.implementation.decorator;

import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;

public class InformationDocumentationDecorator extends DocumentationDecorator {

    public void decorate(Documentation documentation) {

        addText("Thank you for using plugin! I believe it gives you much fun", documentation);

        addText("Documentation is divided to few sections", documentation);
        addListMember(" [Information]   General info about plugin and author", documentation);
        addListMember(" [Commands]      Command, arguments, command permissions", documentation);
        addListMember(" [Permissions]   Permissions and reactions between them", documentation);
        addListMember(" [License]       Plugin license", documentation);
        addText("Be aware modifications of this file has NO impact on plugin", documentation);
        addText("To change plugin behaviour edit config.yml file", documentation);

        addTitle("Information", documentation);


        var yml = createYmlBuilder().
                addSection("information").
                addProperty("author", "JW", 2).
                addProperty("spigot-url", "https://www.facebook.com/", 2).
                addProperty("github-url", "https://www.facebook.com/", 2).
                addProperty("report-bug", "https://www.facebook.com/", 2).
                build();

        addYml(yml,documentation);
    }


}
