package jw.fluent_plugin.implementation.modules.translator;

import jw.fluent_api.translator.api.models.LangData;
import jw.fluent_api.translator.implementation.SimpleLang;

import java.util.List;

public class FluentTranslatorImpl implements FluentTranslator {
    private SimpleLang lang;

    @Override
    public String get(String key) {
        return lang.get(key);
    }

    @Override
    public List<LangData> getLanguages() {
        return lang.getLanguages();
    }

    @Override
    public List<String> getLanguagesName() {
        return getLanguages().stream().map(c -> c.getCountry()).toList();
    }

    @Override
    public boolean setLanguage(String name) {
        return lang.setLanguage(name);
    }

    @Override
    public boolean langAvaliable(String name) {
        return lang.langExists(name);
    }

    @Override
    public void setLanguages(List<LangData> language, String name) {
        lang = new SimpleLang(language);
        lang.setDefaultLang("en");
        lang.setLanguage(name);
    }
}
