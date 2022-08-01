package jw.spigot_fluent_api.fluent_plugin.languages;

import jw.spigot_fluent_api.fluent_plugin.languages.api.models.LangData;
import jw.spigot_fluent_api.fluent_plugin.languages.implementation.SimpleLang;

import java.util.List;

public class Lang {
    static {
        INSTANCE = new Lang();
    }

    private static Lang INSTANCE;
    private SimpleLang simpleLang;

    public static String get(String key) {
        return INSTANCE.simpleLang.get(key);
    }

    public static List<LangData> getLanguages() {
        return INSTANCE.simpleLang.getLanguages();
    }

    public static List<String> getLanguagesName() {
        return getLanguages().stream().map(c -> c.getCountry()).toList();
    }

    public static boolean setLanguage(String name) {
        return INSTANCE.simpleLang.setLanguage(name);
    }

    public static boolean langAvaliable(String name) {
        return INSTANCE.simpleLang.langExists(name);
    }

    public static void setLanguages(List<LangData> language, String name) {
        INSTANCE.simpleLang = new SimpleLang(language);
        INSTANCE.simpleLang.setDefaultLang("en");
        INSTANCE.simpleLang.setLanguage(name);
    }
}
