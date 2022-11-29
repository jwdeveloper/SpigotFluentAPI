package jw.fluent_api.files.api;

public interface SimpleFilesBuilder {

    void addJsonFile(String path, Class toClass);

    void addXmlFile(String path,  Class toClass);

    void addYmlFile(String path,  Class toClass);

    void addCustomFile(String path,  Class toClass);
}
