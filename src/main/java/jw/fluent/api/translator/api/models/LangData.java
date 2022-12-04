package jw.fluent.api.translator.api.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LangData
{
    private String country = "undefind";

    private Map<String, String> translations = new HashMap<>();
}
