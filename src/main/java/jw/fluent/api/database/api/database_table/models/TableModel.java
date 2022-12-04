package jw.fluent.api.database.api.database_table.models;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
public class TableModel {
    private String name;

    private List<ColumnModel> columnList = new ArrayList<>();

    private Class<?> clazz;

    private ColumnModel primaryKeyColumn;

    public Optional<ColumnModel> getColumn(String name)
    {
       return columnList.stream().filter(c -> c.getName().equals(name)).findAny();
    }
    public int getColumnCount()
    {
        int i =0;
        for(var a : columnList)
        {
            if(a.isForeignKey())
            {
                continue;
            }
            i++;
        }
        return i;
    }
    public List<ColumnModel> getForeignKeys()
    {
        return columnList.stream().filter(c -> c.isForeignKey()).toList();
    }
}