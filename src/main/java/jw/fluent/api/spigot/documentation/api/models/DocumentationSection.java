package jw.fluent.api.spigot.documentation.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class DocumentationSection
{
    private String id;

    private SectionType sectionType;

    private String content;

    private List<String> arrtibutes;

    public DocumentationSection(String id, SectionType sectionType, String content) {
        this.id = id;
        this.sectionType = sectionType;
        this.content = content;
        arrtibutes = new ArrayList<>();
    }

    public DocumentationSection(String id, SectionType sectionType, String content, List<String> arrtibutes) {
        this.id = id;
        this.sectionType = sectionType;
        this.content = content;
        this.arrtibutes = arrtibutes;
    }

    public boolean hasAttribute(String attribute)
    {
        for(var att : arrtibutes)
        {
            if(att.equals(attribute))
            {
                return true;
            }
        }
        return false;
    }
}
