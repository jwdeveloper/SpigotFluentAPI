package jw.fluent_api.desing_patterns.unit_of_work.api;

public interface DataContext
{
    void addCustomFileObject(CustomFile object);

    void addYmlObject(Object object);

    void addJsonObject(Object object);

    void addObject(Class<? extends FileHandler> handlerType, Object object);

    void registerFileHandler(FileHandler fileHandler);

    void save();
}
