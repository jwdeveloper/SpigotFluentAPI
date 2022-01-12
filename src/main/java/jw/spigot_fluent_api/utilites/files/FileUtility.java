package jw.spigot_fluent_api.utilites.files;

import com.google.gson.Gson;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public interface FileUtility {
    static String serverPath() {
        return Paths.get("").toAbsolutePath().toString();
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
                    FluentPlugin.logError("Could not delete file: " + childFile.getName());
                }
            }
        }

        if (!file.delete()) {

        }
        FluentPlugin.logError("Could not delete file: " + file.getName());
    }

    static String combinePath(String... paths) {
        var res = "";
        for (var p : paths) {
            res += p + File.separator;
        }
        return res;
    }

    public static boolean save(String content, String path, String fileName) {
        try (FileWriter file = new FileWriter(path+File.separator+fileName))
        {
            file.write(content);
            return true;
        } catch (IOException e) {
            FluentPlugin.logException("Save File: " + fileName,e);
            e.printStackTrace();
        }
        return false;
    }
    static ArrayList<String> getFolderFilesName(String path, String... extensions) {
        final ArrayList<String> filesName = new ArrayList<>();
        if (!isPathValid(path)) {
            FluentPlugin.logError("Files count not be loaded since path " + path + " not exists!");
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

    public static void ensureDirectory(String path) {
        new File(path).mkdirs();
    }
}
