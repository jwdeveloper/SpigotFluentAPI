package jw.fluent.api.spigot.documentation.implementation.renderer;

import jw.fluent.api.spigot.documentation.api.DocumentationRenderer;
import jw.fluent.api.spigot.documentation.api.models.DocumentationSection;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpigotDocumentationRenderer extends DocumentationRenderer {


    @Override
    public String getName() {
        return "documentation-spigot.txt";
    }


    @Override
    protected void onTextSection(MessageBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("spigot-ignore"))
        {
            return;
        }
        if(section.hasAttribute("bold"))
        {
            builder.text("[B]").space().text(section.getContent()).text("[/B]").newLine();
            return;
        }
        super.onTextSection(builder, section);
    }

    @Override
    protected void onTitleSection(MessageBuilder builder, DocumentationSection section) {
        if(section.getId().equals("yml-title"))
        {
            return;
        }

        builder.text("[B]").space().text(section.getContent()).text("[/B]").newLine();
    }

    @Override
    protected void onListSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("[LIST]").newLine();
        builder.text("[*]").text(section.getContent()).newLine();
        builder.text("[/LIST]").newLine();
    }

    @Override
    protected void onImageSection(MessageBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("link"))
        {
            builder.text(" [URL='" + section.getId() + "']");
            builder.text("[IMG]").text(section.getContent()).text("[/IMG]");
            builder.text("[/URL]");
            return;
        }
        builder.text("[CENTER]").text("[IMG]").space().text(section.getContent()).text("[/IMG]").text("[/CENTER]").newLine();
    }

    @Override
    protected void onYmlSection(MessageBuilder builder, DocumentationSection section) {
        builder.text("[code=YAML]").space().text(section.getContent()).text("[/code]").newLine();
    }


    @Override
    protected void onVideoSection(MessageBuilder builder, DocumentationSection section) {
        var content = section.getContent();
        if(content.contains("youtube"))
        {
            var id = getYouTubeId(content);
            builder.text("[CENTER]").text("[MEDIA=youtube]").space().text(id).text("[/MEDIA]").text("[/CENTER]").newLine();
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
