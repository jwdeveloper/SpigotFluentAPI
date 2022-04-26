package jw.spigot_fluent_api.database.mysql_db.change_tacker;

import jw.spigot_fluent_api.database.api.database_table.change_tracker.ChangeTracker;
import jw.spigot_fluent_api.database.mysql_db.models.SqlEntry;
import jw.spigot_fluent_api.database.api.database_table.enums.EntryState;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;

public class SqlChangeTracker<T> implements ChangeTracker<T> {

    private final HashMap<T, SqlEntry<T>> trackedEntries = new HashMap<>();
    private final TableModel tableModel;

    public SqlChangeTracker(final TableModel tableModel) {
        this.tableModel = tableModel;
    }

    @SneakyThrows(IllegalAccessException.class)
    public SqlEntry<T> update(T entity) {
            var sqlEntity = setState(entity, EntryState.UPDATE);
            for (var column : tableModel.getColumnList()) {
                if(column.isForeignKey())
                    continue;

                var newValue = column.getField().get(entity);
                if (sqlEntity.hasFieldValueChanged(column.getName(), newValue)) {
                    sqlEntity.setUpdateField(column.getName(), newValue);
                }
            }
            return sqlEntity;
    }


    @SneakyThrows(IllegalAccessException.class)
    public SqlEntry<T> insert(T entity) {

            final var sqlEntity = setState(entity, EntryState.INSERT);
            sqlEntity.resetValues();
            for (final var column : tableModel.getColumnList()) {
                if(column.isForeignKey())
                    continue;
                final var value = column.getField().get(entity);
                sqlEntity.setField(column.getName(), value);
            }
            return sqlEntity;
    }

    public SqlEntry<T> delete(T entity) {
        return setState(entity, EntryState.DELETE);
    }

    public void clear() {
        for (final var entry :  trackedEntries.values()) {
            if (entry.getAction().equals(EntryState.DELETE)) {
                trackedEntries.remove(entry.getEntity());
                continue;
            }
            entry.setAction(EntryState.NONE);

            for(var set : entry.getUpdatedFields().entrySet())
            {
                entry.setField(set.getKey(),set.getValue());
            }
            entry.getUpdatedFields().clear();
        }
    }

    public List<SqlEntry<T>> getTrackedEntries() {
        return trackedEntries.values().stream().toList();
    }

    private SqlEntry<T> setState(T entity, EntryState action) {

        //TO DO: its too slow method
        for (var entry : trackedEntries.values()) {
            if (entry.getEntity().equals(entity)) {
                entry.setAction(action);
                return entry;
            }
        }

        var entry = new SqlEntry<T>(entity);
        entry.setAction(action);
        entry.setKeyColumnName(tableModel.getPrimaryKeyColumn().getName());
        entry.setKeyField(tableModel.getPrimaryKeyColumn().getField());
        trackedEntries.put(entity, entry);
        return entry;
    }
}
