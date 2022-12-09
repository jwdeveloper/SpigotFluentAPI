package jw.fluent.api.files.implementation.yml.api.models;

import jw.fluent.api.files.implementation.json.JsonUtility;
import jw.fluent.api.utilites.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YmlModel
{
    private String fileName = StringUtils.EMPTY_STRING;

    private String description = StringUtils.EMPTY_STRING;

    private List<YmlContent> contents = new ArrayList<>();

    public void addContent(YmlContent content)
    {
        this.contents.add(content);
    }

    public String toString()
    {
        return JsonUtility.getGson().toJson(this);
    }
}
