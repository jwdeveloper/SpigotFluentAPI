package jw.fluent.api.database.mysql.models;

import jw.fluent.api.database.api.database_table.DbEntry;
import jw.fluent.api.database.api.database_table.enums.EntryState;
import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;



@Data
public class SqlEntry<T> implements DbEntry<T>
{
    private EntryState action;

    private LocalDateTime changedAt;

    private T entity;

    public SqlEntry(T entity) {
        this.entity = entity;
    }

    private HashMap<String,Object> fieldValues = new HashMap<>();

    private HashMap<String,Object> updatedFields = new HashMap<>();

    public void setAction(EntryState action)
    {
        changedAt = LocalDateTime.now();
        this.action = action;
    }

    public void resetValues()
    {
        fieldValues.clear();
        updatedFields.clear();
    }


    public boolean hasFieldValueChanged(String field, Object value)
    {
        var oldValue = fieldValues.get(field);

        if(oldValue == null && value == null)
        {
            return false;
        }

        if(oldValue != null && value == null)
        {
            return true;
        }

        if(oldValue == null)
            return true;

        if(oldValue != value)
        {
            return true;
        }


        return false;
    }

    public void setUpdateField(String field, Object value)
    {
        updatedFields.put(field,value);
    }
    public void setField(String field, Object value)
    {
        fieldValues.put(field,value);
    }

    public Field keyField;

    public String keyColumnName;

    @SneakyThrows
    public void setKey(int key)
    {
        keyField.set(entity,key);
    }

    @SneakyThrows
    public int getKey()
    {
        return keyField.getInt(entity);
    }



}