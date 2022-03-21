package jw.spigot_fluent_api.data.implementation.repositories;

import jw.spigot_fluent_api.data.interfaces.CustomFile;
import jw.spigot_fluent_api.data.implementation.models.DataModel;
import jw.spigot_fluent_api.data.interfaces.Repository;
import jw.spigot_fluent_api.utilites.java.ObjectUtility;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.stream.Stream;

public class RepositoryBase<T extends DataModel> implements Repository<T>, CustomFile {

    private ArrayList<T> content;
    private String fileName;
    private final String path;
    private final Class<T> entityClass;

    public RepositoryBase(String path, Class<T> entityClass) {
        this.content = new ArrayList<>();
        this.path = path;
        this.fileName = entityClass.getSimpleName();
        this.entityClass = entityClass;
    }

    public RepositoryBase(String path, Class<T> entityClass, String filename) {
        this(path, entityClass);
        this.fileName = filename;
    }

    public RepositoryBase(Class<T> entityClass) {
        this(FluentPlugin.getPath(), entityClass, entityClass.getSimpleName());
    }

    public Stream<T> query() {
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
                .filter(p -> p.getUuid().equals(id))
                .findFirst();
    }

    public Optional<T> getOneByName(String name) {
        return content
                .stream()
                .filter(p -> ChatColor.stripColor(p.getName())
                        .equalsIgnoreCase(name))
                .findFirst();
    }

    public ArrayList<T> getMany() {
        return content;
    }

    @Override
    public boolean insertOne(T data) {
        if (data == null) {
            return false;
        }
        if (data.getUuid() == null)
            data.setUuid(UUID.randomUUID());
        content.add(data);
        return true;

    }

    @Override
    public boolean insertMany(List<T> data) {
        data.forEach(this::insertOne);
        return true;
    }

    @Override
    public boolean updateOne(UUID id, T data) {
        var optional = getOne(id);
        if (optional.isEmpty())
            return false;

        return ObjectUtility.copyToObject(data, optional.get(), entityClass);
    }

    @Override
    public boolean updateMany(HashMap<UUID, T> data) {
        data.forEach(this::updateOne);
        return true;
    }

    @Override
    public boolean deleteOne(T data) {
        if (content.contains(data)) {
            content.remove(data);
            return true;
        }
        return false;
    }

    public boolean deleteOneById(UUID uuid) {
        var data = getOne(uuid);
        if (data.isEmpty())
            return false;

        content.remove(data.get());
        return true;
    }

    @Override
    public boolean deleteMany(List<T> data) {
        data.forEach(a -> this.deleteOneById(a.getUuid()));
        return true;
    }

    public void deleteAll() {
        content.clear();
    }

    public int getSize() {
        return content.size();
    }

    public boolean contains(T data) {
        return content.contains(data);
    }

    @Override
    public boolean load() {
        try {
            content = JsonUtility.loadList(path, fileName, entityClass);
            return true;
        } catch (Exception e) {

            FluentPlugin.logException("Repository load error "+fileName + " " + entityClass.getName(), e);
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            JsonUtility.save(content, path, fileName);
            return true;
        } catch (Exception e) {
            FluentPlugin.logException("Repository save error "+fileName + " " + entityClass.getName(), e);
            return false;
        }
    }
}
