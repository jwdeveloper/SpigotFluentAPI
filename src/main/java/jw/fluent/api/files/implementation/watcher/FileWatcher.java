package jw.fluent.api.files.implementation.watcher;


import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

import java.io.File;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FileWatcher extends Thread {
    private final File file;
    private AtomicBoolean stop = new AtomicBoolean(false);

    private LinkedList<Consumer<Path>> onFileChangedEvents;

    public FileWatcher(File file) {
        this.file = file;
        onFileChangedEvents = new LinkedList<>();
    }

    public boolean isStopped() { return stop.get(); }
    public void stopThread() { stop.set(true); }

    public synchronized void onFileChanged(Consumer<Path> event) {


        onFileChangedEvents.add(event);
        FluentLogger.LOGGER.info("EVENT ADDED",onFileChangedEvents);
    }

    @Override
    public void run() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = file.toPath().getParent();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (!isStopped()) {
                WatchKey key;
                try { key = watcher.poll(25, TimeUnit.MILLISECONDS); }
                catch (InterruptedException e) { return; }
                if (key == null) { Thread.yield(); continue; }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
                            && filename.toString().equals(file.getName())) {
                        for(var e : onFileChangedEvents)
                        {
                            e.accept(filename);
                        }
                    }
                    boolean valid = key.reset();
                    if (!valid) { break; }
                }
                Thread.yield();
            }
        } catch (Throwable e) {
            FluentLogger.LOGGER.info("File watcher error",e);
        }
    }
}