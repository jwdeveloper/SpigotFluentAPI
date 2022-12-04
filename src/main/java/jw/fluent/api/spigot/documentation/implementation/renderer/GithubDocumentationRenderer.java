package jw.fluent.api.spigot.documentation.implementation.renderer;

import jw.fluent.api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent.api.spigot.documentation.api.models.DocumentationSection;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubDocumentationRenderer extends DocumentationRenderer {

    @Override
    public String getName() {
        return "documentation-github.md";
    }

    @Override
    protected void onTextSection(MessageBuilder builder, DocumentationSection section) {
        builder.text(">").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onTitleSection(MessageBuilder builder, DocumentationSection section) {
        if(section.getId().equals("yml-title"))
        {
            return;
        }
        builder.text("##").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onImageSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("![alt text]").text("(").text(section.getContent()).text(")").newLine();
    }

    @Override
    protected void onListSection(MessageBuilder builder, DocumentationSection section) {
        builder.text(" - ").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onYmlSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("``` yaml").newLine();
        builder.text(section.getContent()).newLine();
        builder.text("```").newLine();
    }

    @Override
    protected void onVideoSection(MessageBuilder builder, DocumentationSection section) {
        var content = section.getContent();
        if(content.contains("youtube"))
        {
            var id = getYouTubeId(content);
            var imageUrl = "https://img.youtube.com/vi/"+id+"/0.jpg";
            builder.text("[![IMAGE ALT TEXT HERE]").text("(").text(imageUrl).text(")]")
                    .text("(").text(section.getContent()).text(")").newLine();
        }
    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return StringUtils.EMPTY_STRING;
        }
    }
}
