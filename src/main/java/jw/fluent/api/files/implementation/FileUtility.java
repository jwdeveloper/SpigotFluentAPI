package jw.fluent.api.files.implementation;

import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public interface FileUtility {
    static String serverPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

    static String separator() {
        return File.separator;
    }

    static String pluginsPath() {
        return serverPath() + File.separator + "plugins";
    }

    static boolean isPathValid(String path) {
        return new File(path).exists();
    }

    static void removeDirectory(File file) {
        if (file == null)
            return;

        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (File childFile : files) {
                    if (childFile.isDirectory())
                        removeDirectory(childFile);
                    else if (!childFile.delete()) {

                    }
                    FluentLogger.LOGGER.info("Could not delete path: " + childFile.getName());
                }
            }
        }

        if (!file.delete()) {

        }
        FluentLogger.LOGGER.info("Could not delete path: " + file.getName());
    }

    static String pluginPath(JavaPlugin javaPlugin) {

        return javaPlugin.getDataFolder().getAbsoluteFile().toString();
    }


    static boolean downloadFile(String url, String outputPath) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        return true;
    }

    static File pluginFile(JavaPlugin plugin) {
        try {
            var fileField = JavaPlugin.class.getDeclaredField("file");
            fileField.setAccessible(true);
            return (File) fileField.get(plugin);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Can not load plugin path", e);
            return null;
        }
    }

    static String combinePath(String... paths) {
        var res = "";
        for (var p : paths) {
            res += p + File.separator;
        }
        return res;
    }

    static boolean save(String content, String path, String fileName) {
        try (FileWriter file = new FileWriter(path + File.separator + fileName)) {
            file.write(content);
            return true;
        } catch (IOException e) {
            FluentLogger.LOGGER.error("Save File: " + fileName, e);
            e.printStackTrace();
        }
        return false;
    }

    static ArrayList<String> getFolderFilesName(String path, String... extensions) {
        final ArrayList<String> filesName = new ArrayList<>();
        if (!isPathValid(path)) {
            FluentLogger.LOGGER.info("Files count not be loaded since path " + path + " not exists!");
            return filesName;
        }
        final File folder = new File(path);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                // recursion Get_FileNames
            } else {
                if (extensions.length == 0) {
                    filesName.add(fileEntry.getName());
                    continue;
                }

                String name = fileEntry.getName();
                int dotIndex = name.lastIndexOf('.');
                String extension = name.substring(dotIndex + 1);
                for (String fileName : extensions) {
                    if (extension.equalsIgnoreCase(fileName.toLowerCase())) {
                        filesName.add(fileEntry.getName());
                        break;
                    }
                }
            }
        }
        return filesName;
    }


    static boolean pathExists(String path) {
        var directory = new File(path);
        return directory.exists();
    }

    static File ensurePath(String path) {
        var directory = new File(path);
        if (directory.exists()) {
            return directory;
        }
        directory.mkdir();
        return directory;
    }
}
