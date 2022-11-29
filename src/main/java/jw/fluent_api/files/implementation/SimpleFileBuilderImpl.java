package jw.fluent_api.files.implementation;

import jw.fluent_api.files.api.SimpleFilesBuilder;
import jw.fluent_plugin.implementation.modules.files.FluentFilesImpl;

public class SimpleFileBuilderImpl implements SimpleFilesBuilder {
    @Override
    public void addJsonFile(String path, Class toClass) {

    }

    @Override
    public void addXmlFile(String path, Class toClass) {

    }

    @Override
    public void addYmlFile(String path, Class toClass) {

    }

    @Override
    public void addCustomFile(String path, Class toClass) {

    }

    public FluentFilesImpl build()
    {
        return new FluentFilesImpl();
    }
}
