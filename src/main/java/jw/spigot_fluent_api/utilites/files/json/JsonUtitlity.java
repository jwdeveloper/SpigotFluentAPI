package jw.spigot_fluent_api.utilites.files.json;

import com.google.gson.*;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.files.json.adapters.ItemStackAdapter;
import jw.spigot_fluent_api.utilites.files.json.adapters.LocationAdapter;
import jw.spigot_fluent_api.utilites.files.json.execution.BindingFieldSkip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class JsonUtitlity implements FileUtility
{
    public static boolean save(Object data, String path, String fileName) {
        String fullPath =getFullPath(path,fileName);
        if(!FileUtility.isPathValid(fullPath))
        {
            ensureFile(path,fileName,"{}");
        }
        try (FileWriter file = new FileWriter(fullPath))
        {
            Gson gson = getGson();
            file.write(gson.toJson(data));
            return true;
        } catch (IOException e) {
            FluentPlugin.logError("Save File: " + fullPath);
            e.printStackTrace();
        }
        return false;
    }

    public static <T> T load(String path, String fileName, Class<T> type) {
        String fullPath = getFullPath(path,fileName);
        if(!FileUtility.isPathValid(path))
        {
            ensureFile(path,fileName,"{}");
        }
        try (FileReader reader = new FileReader(fullPath)) {

            Gson gson = getGson();
            T res = gson.fromJson(reader, type);
            reader.close();
            return res;
        } catch (IOException e) {
            FluentPlugin.logError("Load File: " + fullPath);
        }
        return null;
    }

    public static <T> ArrayList<T> loadList(String path, String fileName, Class<T> type) {
        ArrayList<T> result = new ArrayList<>();
        String fullPath =getFullPath(path,fileName);
        if(!FileUtility.isPathValid(fullPath))
        {
            ensureFile(path,fileName,"[]");
        }
        try (FileReader reader = new FileReader(fullPath)) {
            Gson gson = getGson();
            JsonArray jsonArray = new JsonParser().parse(reader).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
                result.add(gson.fromJson(jsonElement, type));
            reader.close();
            return result;
        } catch (IOException e) {
            FluentPlugin.logError("Load File: " + fullPath);
        }
        return result;
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
                .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
                .setExclusionStrategies(new BindingFieldSkip())
                .setPrettyPrinting()
                .create();
    }

    private static void ensureFile(String path,String fileName,String content)
    {
        final String fullPath =getFullPath(path,fileName);
        final File file = new File(fullPath);
        if (file.exists())
        {
            return;
        }
        try
        {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        }
        catch (IOException exception)
        {
            FluentPlugin.logError("Creating file error "+exception.getMessage() + "  " + fullPath);
        }
    }



    private static String getFullPath(String path,String fileName)
    {
        return path+File.separator+fileName+".json";
    }
}
