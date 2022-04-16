package jw.spigot_fluent_api.database.mysql_db.query_fluent;

import jw.spigot_fluent_api.database.api.database_table.models.ColumnModel;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlQueryMapper<T> {
    TableModel tableModel;
    ResultSet resultSet;
    Set<ColumnModel> referenceColumns;

    public SqlQueryMapper(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public SqlQueryMapper<T> setTable(TableModel tableModel) {
        this.tableModel = tableModel;
        return this;
    }

    public SqlQueryMapper<T> setJoins(Set<ColumnModel> set) {
        referenceColumns = set;
        return this;
    }

    @SneakyThrows
    public List<T> toList() {
        var columns = getModelConfig();
        var result = new ArrayList<T>();
        T mainObj = null;
        int i = 0;
        while (resultSet.next()) {
            var currentTable = columns.get(i);
            if (i == 0) {
                mainObj = (T) createInstance(resultSet, currentTable);
                i++;
                continue;
            }
            var reference = createInstance(resultSet, currentTable);
            currentTable.referenceColumn.getField().set(mainObj, reference);
            result.add(mainObj);
        }
        return result;
    }

    @SneakyThrows
    private List<CreateObjectDto> getModelConfig() {
        var result = new ArrayList<CreateObjectDto>();
        var metaData = resultSet.getMetaData();
        var resultColumnsCount = metaData.getColumnCount();

        var currentTable = tableModel;
        var currentTableColumns = currentTable.getColumnCount();
        var to = Math.min(resultColumnsCount, currentTableColumns);
        var dto = new CreateObjectDto();
        dto.tableModel = currentTable;
        dto.objectType = currentTable.getClazz();
        for (int i = 1; i <= to; i++) {
            var colName = metaData.getColumnLabel(i);
            var columnInfo = tableModel.getColumn(colName);
            if (columnInfo.isEmpty())
                continue;
            dto.columnModels.put(i,columnInfo.get());
        }

        return result;
    }


    @SneakyThrows
    public Object createInstance(ResultSet result, CreateObjectDto createObj) throws InstantiationException, IllegalAccessException, SQLException {
        Object instnace = createObj.objectType.newInstance();
        for (var entrySet : createObj.columnModels.entrySet()) {
            var value = result.getObject(entrySet.getKey());
            var column = entrySet.getValue();

            if (value == null && !column.isRequired()) {
                column.getField().set(instnace, null);
                continue;
            }

            var fieldType = column.getField().getType().getSimpleName();
            if (fieldType.equals("UUID")) {
                column.getField().set(instnace, UUID.fromString(value.toString()));
                continue;
            }
            column.getField().set(instnace, value);
        }
        return instnace;
    }

    public class CreateObjectDto {
        public ColumnModel referenceColumn;
        public Class objectType;
        public TableModel tableModel;
        public HashMap<Integer, ColumnModel> columnModels = new HashMap<Integer, ColumnModel>();
    }
}
