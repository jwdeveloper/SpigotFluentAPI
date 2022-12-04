package jw.fluent.api.spigot.documentation.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DocumentationSection
{
    private String id;

    private SectionType sectionType;

    private String content;
}
