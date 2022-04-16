package jw.spigot_fluent_api.database.api.database_table;

import java.sql.SQLException;

public interface DbTable<T>
{
     DbEntry<T> update(T entity);

     DbEntry<T> insert(T entity);

     DbEntry<T> delete(T entity);

     void saveChanges() throws SQLException;
}
