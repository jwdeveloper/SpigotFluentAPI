package jw.fluent.api.files.api;

import java.util.*;

public interface Repository <T, Key>
{
     Class<T> getEntityClass();

     Optional<T> findById(Key id);

     List<T> findAll();

     boolean insert(T data);

     boolean update(Key id, T data);

     boolean deleteOne(T data);

     boolean deleteMany(List<T> data);
}
