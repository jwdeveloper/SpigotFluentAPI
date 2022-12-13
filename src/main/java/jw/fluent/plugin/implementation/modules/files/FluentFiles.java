package jw.fluent.plugin.implementation.modules.files;

import jw.fluent.api.files.implementation.watcher.FileWatcher;

import java.util.function.Consumer;

public interface FluentFiles {

    void load() throws Exception;

    void save();

    FileWatcher createFileWatcher(String path);
}
