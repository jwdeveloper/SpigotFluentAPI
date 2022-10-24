package jw.fluent_api.utilites.files;

import jw.fluent_api.logger.OldLogger;

import java.io.*;
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
                    OldLogger.info("Could not delete file: " + childFile.getName());
                }
            }
        }

        if (!file.delete()) {

        }
        OldLogger.info("Could not delete file: " + file.getName());
    }

    static String combinePath(String... paths) {
        var res = "";
        for (var p : paths) {
            res += p + File.separator;
        }
        return res;
    }

     static boolean save(String content, String path, String fileName) {
        try (FileWriter file = new FileWriter(path+File.separator+fileName))
        {
            file.write(content);
            return true;
        } catch (IOException e) {
            OldLogger.error("Save File: " + fileName,e);
            e.printStackTrace();
        }
        return false;
    }
    static ArrayList<String> getFolderFilesName(String path, String... extensions) {
        final ArrayList<String> filesName = new ArrayList<>();
        if (!isPathValid(path)) {
            OldLogger.info("Files count not be loaded since path " + path + " not exists!");
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


    static boolean pathExists(String path)
    {
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