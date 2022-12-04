package jw.fluent.api.files.api;

public interface DataContext
{
    void addCustomFileObject(CustomFile object);

    void addYmlObject(Object object);

    void addJsonObject(Object object);

    void addObject(Class<? extends FileHandler> handlerType, Object object);

    void registerFileHandler(FileHandler fileHandler);

    void save();
}
