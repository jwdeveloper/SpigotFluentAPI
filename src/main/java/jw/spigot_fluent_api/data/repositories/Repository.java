package jw.spigot_fluent_api.data.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface Repository <T>
{
     Class<T> getEntityClass();

     T getOne(UUID id);

     ArrayList<T> getMany(HashMap<String,String> args);

     ArrayList<T> getMany();

     boolean insertOne(T data);

     boolean insertMany(ArrayList<T> data);

     boolean updateOne(UUID id,T data);

     boolean updateMany(HashMap<String,T> data);

     boolean deleteOne(T data);

     boolean deleteMany(ArrayList<T> data);
}
