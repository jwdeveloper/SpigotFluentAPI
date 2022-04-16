package jw.spigot_fluent_api.utilites.files.json;

import com.google.gson.*;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.files.json.adapters.DateTimeOffsetAdapter;
import jw.spigot_fluent_api.utilites.files.json.adapters.ItemStackAdapter;
import jw.spigot_fluent_api.utilites.files.json.adapters.LocationAdapter;
import jw.spigot_fluent_api.utilites.files.json.execution.BindingFieldSkip;
import jw.spigot_fluent_api.utilites.files.json.execution.JsonIgnoreAnnotationSkip;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public final class JsonUtility implements FileUtility {
    public static boolean save(Object data, String path, String fileName) {
        String fullPath = getFullPath(path, fileName);
        if (!FileUtility.isPathValid(fullPath)) {
            ensureFile(path, fileName, "{}");
        }
        try (FileWriter file = new FileWriter(fullPath)) {
            Gson gson = getGson();
            file.write(gson.toJson(data));
            return true;
        } catch (IOException e) {
            FluentLogger.error("Save File: " + fullPath, e);
            e.printStackTrace();
        }
        return false;
    }

    public static <T> T load(String path, String fileName, Class<T> type) {
        String fullPath = getFullPath(path, fileName);
        if (!FileUtility.isPathValid(path)) {
            ensureFile(path, fileName, "{}");
        }
        try (FileReader reader = new FileReader(fullPath)) {

            Gson gson = getGson();
            T res = gson.fromJson(reader, type);
            reader.close();
            return res;
        } catch (IOException e) {
            FluentLogger.error("Load File: " + fullPath, e);
        }
        return null;
    }

    public static <T> ArrayList<T> loadList(String path, String fileName, Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        String fullPath = getFullPath(path, fileName);
        if (!FileUtility.isPathValid(fullPath)) {
            ensureFile(path, fileName, "[]");
        }
        try (FileReader reader = new FileReader(fullPath)) {
            Gson gson = getGson();
            JsonArray jsonArray = new JsonParser().parse(reader).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
                result.add(gson.fromJson(jsonElement, type));
            reader.close();
            return result;
        } catch (IOException e) {
            FluentLogger.error("Load File: " + fullPath, e);
        }
        return result;
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
                .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
                .registerTypeHierarchyAdapter(OffsetDateTime.class, new DateTimeOffsetAdapter())
                .setExclusionStrategies(new BindingFieldSkip())
                .setExclusionStrategies(new JsonIgnoreAnnotationSkip())
                .setPrettyPrinting()
                .create();
    }

    public static void ensureFile(String path, String fileName, String content) {
        final String fullPath = getFullPath(path, fileName);
        final File file = new File(fullPath);
        if (file.exists()) {
            return;
        }
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException exception) {
            FluentLogger.error("Creating file error " + exception.getMessage() + "  " + fullPath, exception);
        }
    }


    private static String getFullPath(String path, String fileName) {
        return path + File.separator + fileName + ".json";
    }
}
