package jw.spigot_fluent_api.data.repositories;

import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.data.models.DataModel;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class RepositoryBase<T extends DataModel> implements Repository<T> , Saveable {

    private ArrayList<T> content;
    private String fileName;
    private final String path;
    private final Class<T> entityClass;


    public Consumer<String> onError = (s) -> {
    };

    public RepositoryBase(String path, Class<T> entityClass)
    {
        this.content = new ArrayList<>();
        this.path = path;
        this.fileName = entityClass.getSimpleName();
        this.entityClass = entityClass;
    }

    public RepositoryBase(String path, Class<T> entityClass, String filename) {
        this(path, entityClass);
        this.fileName = filename;
    }
    public RepositoryBase(Class<T> entityClass)
    {
        this(FluentPlugin.getPath(),entityClass,entityClass.getSimpleName());
    }

    public Stream<T> query()
    {
        return content.stream();
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Optional<T> getOne(UUID id) {
      return content
                .stream()
                .filter(p -> p.uuid == id)
                .findFirst();
    }

    public Optional<T> getOneByName(String name) {
      return content
                .stream()
                .filter(p -> ChatColor.stripColor(p.name)
                .equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public ArrayList<T> getMany(HashMap<String, String> args) {
        return content;
    }

    public ArrayList<T> getMany() {
        return content;
    }

    @Override
    public boolean insertOne(T data) {
        if (data != null) {
            data.uuid = UUID.randomUUID();
            content.add(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertMany(List<T> data) {
        data.forEach(this::insertOne);
        return true;
    }

    @Override
    public boolean updateOne(UUID id, T data)
    {
        return false;
    }

    @Override
    public boolean updateMany(HashMap<String, T> data) {
        return false;
    }

    @Override
    public boolean deleteOne(T data)
    {
        if ( content.contains(data)) {
            content.remove(data);
            return true;
        }
        return false;
    }
    public boolean deleteOneById(UUID uuid)
    {
        var data = getOne(uuid);
        if(data.isEmpty())
            return false;

        content.remove(data.get());
        return true;
    }
    @Override
    public boolean deleteMany(List<T> data)
    {
        data.forEach(a -> this.deleteOneById(a.uuid));
        return true;
    }

    public void deleteAll() {
        content.clear();
    }

    public T createEmpty() {
        try {
            T empty = entityClass.newInstance();
            empty.uuid = null;
            empty.name = "-1";
            return empty;
        } catch (IllegalAccessException | InstantiationException igonre) {
            onError.accept(igonre.getMessage());
        }
        return null;
    }

    public boolean contains(T data)
    {
        return content.contains(data);
    }

    @Override
    public boolean load()
    {
            try {
                content = JsonUtility.loadList(path, fileName, entityClass);
                return true;
            } catch (Exception e)
            {
                onError.accept(fileName + " " + entityClass.getName() + " " + e.getMessage());
                return false;
            }
    }

    @Override
    public boolean save() {
        try
        {
            JsonUtility.save(content, path, fileName);
            return true;
        } catch (Exception e)
        {
            onError.accept(fileName + " " + entityClass.getName() + " " + e.getMessage());
            return false;
        }
    }
}
