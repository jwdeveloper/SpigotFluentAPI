package jw.fluent.plugin.implementation.modules.translator;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.translator.api.models.LangData;
import jw.fluent.api.translator.implementation.SimpleLang;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

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
    @SneakyThrows
    public void generateEmptyTranlations() {
        FluentLogger.LOGGER.log("Generating empty tranlations");
        var Optional = getLanguages().stream().filter(f -> f.getCountry().equals("en")).findFirst();
        var eng = Optional.get();


        var path = FluentApi.path()+ FileUtility.separator()+"temp";
        FileUtility.ensurePath(path);
        for(var lang : getLanguages())
        {
            if(eng.getCountry().equals(lang.getCountry()))
            {
                continue;
            }
            FluentLogger.LOGGER.info("========================= Language",lang.getCountry()," =================================");
            FluentLogger.LOGGER.info("Tital paths", lang.getTranslations().size());
            var configuration = new YamlConfiguration();
            for(var yamlPath : eng.getTranslations().keySet())
            {

                var value = StringUtils.EMPTY;
                if(lang.getTranslations().containsKey(yamlPath))
                {
                    FluentLogger.LOGGER.success("Language",lang.getCountry(),yamlPath);
                    value = lang.getTranslations().get(yamlPath);
                }
                else
                {
                    FluentLogger.LOGGER.warning("Language",lang.getCountry(),yamlPath);
                    value = "<!EMPTY!> " +eng.getTranslations().get(yamlPath);
                }
                configuration.set(yamlPath, value);
            }
            var path2 = path+ FileUtility.separator()+lang.getCountry()+".yml";
            configuration.save(path2);
        }
        FluentLogger.LOGGER.log("Generating done");
    }

    public void setLanguages(List<LangData> language, String name) {
        lang = new SimpleLang(language);
        lang.setDefaultLang("en");
        lang.setLanguage(name);
    }
}
