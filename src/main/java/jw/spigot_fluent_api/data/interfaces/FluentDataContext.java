package jw.spigot_fluent_api.data.interfaces;

public interface FluentDataContext
{
    void addCustomFileObject(CustomFile object);

    void addYmlObject(Object object);

    void addJsonObject(Object object);

    void addObject(Class<? extends FileHandler> handlerType, Object object);

    void registerFileHandler(FileHandler fileHandler);

    void save();
}
