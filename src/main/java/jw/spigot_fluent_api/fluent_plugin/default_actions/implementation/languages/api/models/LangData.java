package jw.spigot_fluent_api.fluent_plugin.default_actions.implementation.languages.api.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LangData
{
    private String country = "undefind";

    private Map<String, String> translations = new HashMap<>();
}
