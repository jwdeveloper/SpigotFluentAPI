package jw.spigot_fluent_api.data_models;

import jw.spigot_fluent_api.data_models.repositories.Repository;
import jw.spigot_fluent_api.utilites.files.json.JsonUtitlity;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.ObjectUtility;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class DataManager
{
    protected final String path;
    protected final List<Saveable> saveables = new ArrayList<>();

    public DataManager() {
        this(FluentPlugin.getPath());
    }

    public DataManager(String path)
    {
        this.path = path;
    }

    public List<Saveable> getSaveableObjects()
    {
        return saveables;
    }

    public void addSaveableObject(Saveable object) {
        saveables.add(object);
    }

    public <T extends Saveable> void addSaveableObject(Class<T> tClass)
    {
        try
        {
          saveables.add(tClass.newInstance());
        }
        catch (Exception e)
        {

        }
    }

    public <T extends Saveable> T getSaveableObject(Class<T> tClass)
    {
        for (var saveable: saveables)
        {
          if(saveable.getClass().equals(tClass))
          {
              return (T)saveable;
          }
        }
       return null;
    }

    public void addSaveableObject(List<Saveable> objects) {
        saveables.addAll(objects);
    }

    public void load() {
        for (var saveable : saveables) {
            if (saveable instanceof Repository)
                saveable.load();
            else {
                try {
                    var loadedObject = JsonUtitlity.load(
                            path,
                            saveable.getClass().getSimpleName(),
                            saveable.getClass());

                    if (loadedObject != null) {
                        ObjectUtility.copyToObject(loadedObject, saveable, loadedObject.getClass());
                    }

                } catch (Exception e) {
                    Bukkit.getServer().getConsoleSender().sendMessage("Loading data from file " + e.getMessage().toString());
                }
            }

        }
    }

    public void save() {
        for (Saveable saveable : saveables) {
            if (saveable instanceof Repository)
                saveable.load();
            else
                JsonUtitlity.save(saveable, path, saveable.getClass().getSimpleName());
        }
    }
}