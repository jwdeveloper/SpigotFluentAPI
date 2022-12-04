package jw.fluent.api.spigot.documentation.implementation;

import jw.fluent.api.spigot.documentation.api.DocumentationRenderer;

public class InformationDocumentationRenderer extends DocumentationRenderer {

    @Override
    public String render() {
        addComment("Thank you for using plugin! I believe it gives you much fun");


        addComment("Documentation is divided to few sections");
        builder.newLine();
        addComment(" [Information]   General info about plugin and author");
        addComment(" [Commands]      Command, arguments, command permissions");
        addComment(" [Permissions]   Permissions and reactions between them");
        addComment(" [License]       Plugin license");
        builder.newLine();
        addComment("Be aware modifications of this file has NO impact on plugin");
        addComment("To change plugin behaviour edit config.yml file");

        renderTitle("Information");

        ymlField("information");
        ymlField("author","JW",2);
        ymlField("spigot-url","https://www.facebook.com/",2);
        ymlField("github-url","https://www.facebook.com/",2);
        ymlField("report-bug","https://www.facebook.com/",2);
        return builder.build();
    }


}
