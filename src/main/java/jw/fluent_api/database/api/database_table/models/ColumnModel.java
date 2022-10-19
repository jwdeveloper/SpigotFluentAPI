package jw.fluent_api.database.api.database_table.models;

import jw.fluent_api.database.api.database_table.enums.ReferenceOption;
import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ColumnModel {

    private String name ="";

    private boolean isRequired;

    private String type;

    private int size;

    private boolean isKey;

    private boolean isPrimaryKey;

    private boolean isAutoIncrement;

    private boolean isForeignKey;

    private String foreignKeyReference;

    private String foreignKeyName;

    private String foreignKeyTableName;

    private ReferenceOption onDelete;

    public String getOnDeleteString()
    {
        return onDelete.name().replace("_"," ");
    }


    private ReferenceOption onUpdate;

    private Field field;

    public void setField(Field field) {
        field.setAccessible(true);
        this.field = field;
    }
}