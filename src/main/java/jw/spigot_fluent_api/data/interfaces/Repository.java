package jw.spigot_fluent_api.data.interfaces;

import java.util.*;

public interface Repository <T>
{
     Class<T> getEntityClass();

     Optional<T> getOne(UUID id);

     ArrayList<T> getMany();

     boolean insertOne(T data);

     boolean insertMany(List<T> data);

     boolean updateOne(UUID id,T data);

     boolean updateMany(HashMap<UUID,T> data);

     boolean deleteOne(T data);

     boolean deleteMany(List<T> data);
}
