package jw.spigot_fluent_api.data_models.repositories;

import jw.spigot_fluent_api.data_models.Saveable;
import jw.spigot_fluent_api.data_models.models.DataModel;
import jw.spigot_fluent_api.utilites.files.json.JsonUtitlity;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class RepositoryBase<T extends DataModel> implements Repository<T> , Saveable {

    public ArrayList<T> content = new ArrayList<>();
    private String path;
    private String fileName;
    private Class<T> entityClass;

    public Consumer<String> onError = (s) -> {
    };

    public RepositoryBase(String path, Class<T> entityClass) {
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
        this(FluentPlugin.getPath(),entityClass,entityClass.getName());
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T getOne(String id) {
        Optional<T> data = content
                .stream()
                .filter(p -> p.id.equalsIgnoreCase(id))
                .findFirst();
        return data.orElseGet(this::CreateEmpty);
    }

    public T getOneByName(String name) {
        Optional<T> data = content
                .stream()
                .filter(p -> ChatColor.stripColor(p.name)
                .equalsIgnoreCase(name))
                .findFirst();
        return data.orElseGet(this::CreateEmpty);
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
            data.id = UUID.randomUUID().toString();
            content.add(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertMany(ArrayList<T> data) {
        data.stream().forEach(this::insertOne);
        return true;
    }

    @Override
    public boolean updateOne(String id, T data) {
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
    public boolean deleteOneById(String id)
    {
        Optional<T> exist = content.stream()
                .filter(p -> p.id.equalsIgnoreCase(id))
                .findFirst();
        if (exist.isPresent()) {
            content.remove(exist.get());
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteMany(ArrayList<T> data) {
        data.stream().forEach(a -> this.deleteOneById(a.id));
        return true;
    }

    public void deleteAll() {
        content.clear();
    }



    public T CreateEmpty() {
        try {
            T empty = entityClass.newInstance();
            empty.id = "-1";
            empty.name = "-1";
            return empty;
        } catch (IllegalAccessException | InstantiationException igonre) {
            onError.accept(igonre.getMessage());
        }
        return null;
    }

    @Override
    public boolean load()
    {
            try {
                content = JsonUtitlity.loadList(path, fileName, entityClass);
                return true;
            } catch (Exception e) {
                onError.accept(fileName + " " + entityClass.getName() + " " + e.getMessage());
                return false;
            }
    }

    @Override
    public boolean save() {
        try {
            JsonUtitlity.save(content, path, fileName);
            return true;
        } catch (Exception e) {
            onError.accept(fileName + " " + entityClass.getName() + " " + e.getMessage());
            return false;
        }
    }
}
