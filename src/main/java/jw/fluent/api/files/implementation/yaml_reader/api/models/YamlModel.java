package jw.fluent.api.files.implementation.yaml_reader.api.models;

import jw.fluent.api.files.implementation.json.JsonUtility;
import jw.fluent.api.utilites.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YamlModel
{
    private String fileName = StringUtils.EMPTY;
    private String description = StringUtils.EMPTY;
    private List<YamlContent> contents = new ArrayList<>();

    public void addContent(YamlContent content)
    {
        this.contents.add(content);
    }

    public String toString()
    {
        return JsonUtility.getGson().toJson(this);
    }

    public boolean hasDescription()
    {
        return description.length() != 0;
    }
}
