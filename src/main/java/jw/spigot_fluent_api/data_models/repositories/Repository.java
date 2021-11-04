package jw.spigot_fluent_api.data_models.repositories;

import java.util.ArrayList;
import java.util.HashMap;

public interface Repository <T>
{
     Class<T> getEntityClass();

     T getOne(String id);

     ArrayList<T> getMany(HashMap<String,String> args);

     ArrayList<T> getMany();

     boolean insertOne(T data);

     boolean insertMany(ArrayList<T> data);

     boolean updateOne(String id,T data);

     boolean updateMany(HashMap<String,T> data);

     boolean deleteOne(T data);

     boolean deleteMany(ArrayList<T> data);
}
