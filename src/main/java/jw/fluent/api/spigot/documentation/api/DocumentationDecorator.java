package jw.fluent.api.spigot.documentation.api;

import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.spigot.documentation.api.models.DocumentationSection;
import jw.fluent.api.spigot.documentation.api.models.SectionType;
import jw.fluent.api.spigot.documentation.implementation.builders.YmlBuilder;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.java.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class DocumentationDecorator {
    public abstract void decorate(Documentation documentation);

    protected MessageBuilder createStringBuilder() {
        return FluentMessage.message();
    }

    protected YmlBuilder createYmlBuilder() {
        return new YmlBuilder();
    }

    protected DocumentationDecorator addTitle(String title, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("title", SectionType.TITlE, title));
        return this;
    }

    protected DocumentationDecorator addTitle(String title, Documentation documentation, String id) {
        documentation.getSections().add(new DocumentationSection(id, SectionType.TITlE, title));
        return this;
    }

    protected DocumentationDecorator addCode(String code, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("code", SectionType.CODE, code));
        return this;
    }

    protected DocumentationDecorator addYml(String yml, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("yml", SectionType.YML, yml));
        return this;
    }

    protected DocumentationDecorator addText(Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("text", SectionType.TEXT, StringUtils.EMPTY));
        return this;
    }
    protected DocumentationDecorator addText(String text, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("text", SectionType.TEXT, text));
        return this;
    }

    protected DocumentationDecorator addText(String text, Documentation documentation, String... arrtibutes) {
        documentation.getSections().add(new DocumentationSection("text", SectionType.TEXT, text, Arrays.stream(arrtibutes).toList()));
        return this;
    }

    protected DocumentationDecorator addImage(String url, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("image", SectionType.IMAGE, url));
        return this;
    }

    protected DocumentationDecorator addImageWithLink(String url, String link, Documentation documentation) {
        var att = new ArrayList<String>();
        att.add("link");
        documentation.getSections().add(new DocumentationSection(link, SectionType.IMAGE, url,att));
        return this;
    }

    protected DocumentationDecorator addVideo(String url, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("video", SectionType.VIDEO, url));
        return this;
    }

    protected DocumentationDecorator addLink(String name, String url, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection(name, SectionType.LINK, url));
        return this;
    }

    protected DocumentationDecorator addHtml(String title, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("html", SectionType.HTML, title));
        return this;
    }

    protected DocumentationDecorator addListMember(String member, Documentation documentation) {
        documentation.getSections().add(new DocumentationSection("html", SectionType.LIST, member));
        return this;
    }
}
