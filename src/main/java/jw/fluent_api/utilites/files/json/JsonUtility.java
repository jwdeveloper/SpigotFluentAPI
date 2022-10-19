package jw.fluent_api.utilites.files.json;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.*;
import jw.fluent_api.minecraft.logger.FluentLogger;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.files.json.adapters.DateTimeOffsetAdapter;
import jw.fluent_api.utilites.files.json.adapters.ItemStackAdapter;
import jw.fluent_api.utilites.files.json.adapters.LocationAdapter;
import jw.fluent_api.utilites.files.json.execution.BindingFieldSkip;
import jw.fluent_api.utilites.files.json.execution.JsonIgnoreAnnotationSkip;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.*;
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


    public static <T> T load(InputStream inputStream, Class<T> type) {
        try {
            var json = CharStreams.toString(new InputStreamReader(
                    inputStream, Charsets.UTF_8));
            var gson = getGson();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            FluentLogger.error("Load File: " + type.getSimpleName(), e);
        }
        return null;
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

    public static <T> ArrayList<T> loadList(InputStream inputStream, Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        try {
            var json = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            Gson gson = getGson();
            JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
                result.add(gson.fromJson(jsonElement, type));

            inputStream.close();
            return result;
        } catch (IOException e) {
            FluentLogger.error("Load File: " + type.getSimpleName(), e);
        }
        return result;
    }

    public static <T> ArrayList<T> loadList(String path, String fileName, Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        String fullPath = getFullPath(path, fileName);
        if (!FileUtility.isPathValid(fullPath)) {
            ensureFile(path, fileName, "[]");
        }
        try (FileReader reader = new FileReader(fullPath)) {
            var gson = getGson();
            var jsonArray = new JsonParser().parse(reader).getAsJsonArray();
            for (var jsonElement : jsonArray)
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
