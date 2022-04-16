package jw.spigot_fluent_api.database.json_db.database_table;

import jw.spigot_fluent_api.database.api.database_table.DbEntry;
import jw.spigot_fluent_api.database.api.database_table.DbTable;

import java.sql.SQLException;

public class JsonTable<T> implements DbTable<T>
{

    @Override
    public DbEntry<T> update(T entity) {
        return null;
    }

    @Override
    public DbEntry<T> insert(T entity) {
        return null;
    }

    @Override
    public DbEntry<T> delete(T entity) {
        return null;
    }

    @Override
    public void saveChanges() throws SQLException
    {

    }
}
