package jw.fluent_plugin.implementation.modules.translator;

import jw.fluent_api.translator.api.models.LangData;

import java.util.List;

public interface FluentTranslator {
    String get(String key);

    List<LangData> getLanguages();

    List<String> getLanguagesName();

    boolean setLanguage(String name);

    boolean langAvaliable(String name);

    void setLanguages(List<LangData> language, String name);
}
