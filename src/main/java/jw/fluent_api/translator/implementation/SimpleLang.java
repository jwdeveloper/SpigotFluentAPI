package jw.fluent_api.translator.implementation;

import jw.fluent_api.translator.api.models.LangData;
import jw.fluent_plugin.implementation.FluentApi;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.List;

@Getter
public class SimpleLang {
    private final List<LangData> languages;
    private LangData currentLang;
    private LangData defaultLang;
    private String NOT_FOUND = ChatColor.RED+"TRANSLATION NOT FOUND";

    public SimpleLang(List<LangData> languages)
    {
        this.languages = languages;
    }

    public String get(String key) {
        if (currentLang.getTranslations().containsKey(key)) {
            return currentLang.getTranslations().get(key);
        }

        if (defaultLang.getTranslations().containsKey(key)) {
            return defaultLang.getTranslations().get(key);
        }

        FluentApi.logger().warning(NOT_FOUND+": "+key);
        return NOT_FOUND;
    }


    public boolean setDefaultLang(String name)
    {
        for(var language : languages)
        {
            if(language.getCountry().equals(name))
            {
                defaultLang = language;
                return true;
            }
        }
        FluentApi.logger().warning("Language not found: "+name);
        return false;
    }

    public boolean langExists(String  name)
    {
        return languages.stream().anyMatch(c -> c.getCountry().equals(name));
    }

    public boolean setLanguage(String name)
    {
        for(var language : languages)
        {
            if(language.getCountry().equals(name))
            {
                currentLang = language;
                return true;
            }
        }
        FluentApi.logger().warning("Language not found: "+name);
        return false;
    }



}
