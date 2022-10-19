package jw.fluent_api.database.mysql.factories;

import jw.fluent_api.database.api.database_table.annotations.*;
import jw.fluent_api.database.api.database_table.models.ColumnModel;
import jw.fluent_api.database.api.database_table.models.TableModel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

public class SqlTableModelFactory {
    private static final HashMap<Class<?>, TableModel> catchedTableModes = new HashMap<>();

    public static <T> TableModel getTableModel(Class<T> clazz) {
        if (clazz == null)
            return null;

        if (catchedTableModes.containsKey(clazz)) {
            return catchedTableModes.get(clazz);
        }

        var result = new TableModel();
        result.setName(getTableName(clazz));
        result.setClazz(clazz);

        for (var field : clazz.getDeclaredFields()) {
            var columnResult = getColumn(field);
            if (columnResult.isEmpty())
                continue;

            var column = columnResult.get();
            if (column.isKey()) {
                result.setPrimaryKeyColumn(column);
            }

            result.getColumnList().add(column);
        }

        catchedTableModes.put(clazz, result);
        return result;
    }


    private static <T> String getTableName(Class<T> clazz) {
        var isAnnotation = clazz.isAnnotationPresent(Table.class);
        if (!isAnnotation) {
            return clazz.getSimpleName();
        }
        var annotation = clazz.getAnnotation(Table.class);
        if (annotation.name().isEmpty()) {
            return clazz.getSimpleName();
        }
        return annotation.name();
    }

    private static <T> Optional<ColumnModel> getColumn(Field field) {
        var isColumn = false;
        var result = new ColumnModel();
        for (var annotation : field.getAnnotations()) {

            if (annotation instanceof Column column) {
                isColumn = true;

                if (column.name().isEmpty())
                    result.setName(field.getName());
                else
                    result.setName(column.name());

                result.setField(field);
                result.setType(column.type());
                result.setSize(column.size());
                continue;
            }
            if (annotation instanceof Required) {

                result.setRequired(true);
                continue;
            }

            if (annotation instanceof Key key) {
                result.setKey(true);
                result.setPrimaryKey(key.isPrimary());
                result.setAutoIncrement(key.autoIncrement());
                continue;
            }

            if (annotation instanceof ForeignKey key) {
                isColumn = true;
                var table = getTableModel(field.getType());
                result.setForeignKeyTableName(table.getName());
                result.setForeignKey(true);
                result.setForeignKeyReference(key.referencedColumnName());
                result.setForeignKeyName(key.columnName());
                result.setField(field);
                result.setOnUpdate(key.onUpdate());
                result.setOnDelete(key.onDelete());
                result.setType(field.getType().getSimpleName());
                continue;
            }
        }

        if (!isColumn) {
            return Optional.empty();
        }

        return Optional.of(result);
    }
}
