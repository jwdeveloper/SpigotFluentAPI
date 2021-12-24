package jw.spigot_fluent_api.data.repositories;

import java.util.*;

public interface Repository <T>
{
     Class<T> getEntityClass();

     Optional<T> getOne(UUID id);

     ArrayList<T> getMany(HashMap<String,String> args);

     ArrayList<T> getMany();

     boolean insertOne(T data);

     boolean insertMany(List<T> data);

     boolean updateOne(UUID id,T data);

     boolean updateMany(HashMap<String,T> data);

     boolean deleteOne(T data);

     boolean deleteMany(List<T> data);
}
