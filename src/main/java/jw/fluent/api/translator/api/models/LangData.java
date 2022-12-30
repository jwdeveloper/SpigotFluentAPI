package jw.fluent.api.translator.api.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class LangData
{
    private String country = "undefined";

    private Map<String, String> translations = new ConcurrentHashMap<>();
}
