package jw.fluent.api.spigot.documentation.api.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Documentation
{
    private String name;

    private List<DocumentationSection> sections = new ArrayList<>();
}
