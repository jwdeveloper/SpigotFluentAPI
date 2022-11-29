package jw.fluent_api.database.mysql.query_fluent;

import jw.fluent_api.database.api.database_table.models.ColumnModel;
import jw.fluent_api.database.api.database_table.models.TableModel;
import jw.fluent_api.database.mysql.factories.SqlTableModelFactory;
import jw.fluent_api.utilites.Stopwatch;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class SqlQueryExecutor<T> {
    private TableModel tableModel;
    private ResultSet resultSet;
    private Set<ColumnModel> joinedColumns;

    public SqlQueryExecutor(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.joinedColumns = new HashSet<>();
    }

    public SqlQueryExecutor<T> setTable(TableModel tableModel) {
        this.tableModel = tableModel;
        return this;
    }

    public SqlQueryExecutor<T> setJoins(Set<ColumnModel> set) {
        joinedColumns.addAll(set);
        return this;
    }

    public SqlQueryExecutor<T> setJoins(ColumnModel set) {
        joinedColumns.add(set);
        return this;
    }

    @SneakyThrows
    public List<T> toList() {
        var stopper = new Stopwatch();
        stopper.start();
        var tables = getModelConfig();
        stopper.stop("Get columns info ");

        stopper.start();
        var result = getMappedResult(tables);
        stopper.stop("Get mapped objects ");

        return result;
    }

    public List<T> getMappedResult(List<MappingTableDto> columnsInfo) throws SQLException, InstantiationException, IllegalAccessException {
        var result = new ArrayList<T>();

        while (resultSet.next()) {
            T mainObj = null;
            for(int i=0;i<columnsInfo.size();i++)
            {
                var currentTable = columnsInfo.get(i);
                if(i == 0)
                {
                    mainObj = (T) createInstance(resultSet, currentTable);
                    continue;
                }
                var reference = createInstance(resultSet, currentTable);
                currentTable.joinedColumn.getField().set(mainObj, reference);
            }
            result.add(mainObj);
        }
        return result;
    }

    @SneakyThrows
    private List<MappingTableDto> getModelConfig() {
        var result = new ArrayList<MappingTableDto>();
        var meta = resultSet.getMetaData();
        result.add(getTable(tableModel,resultSet.getMetaData(),0));

        var size= tableModel.getColumnCount();
        for(var ref : joinedColumns)
        {
            var table = SqlTableModelFactory.getTableModel(ref.getField().getType());
            var dto = getTable(table,meta, size);
            dto.joinedColumn = ref;
            size += table.getColumnCount();
            result.add(dto);
        }

        return result;
    }

    public MappingTableDto getTable(TableModel tableModel, ResultSetMetaData metaData, int start) throws SQLException {
        var currentTableColumns = tableModel.getColumnCount();
        var resultColumnsCount = metaData.getColumnCount();
        var to = Math.min(resultColumnsCount, currentTableColumns);
        var dto = new MappingTableDto();
        dto.tableModel = tableModel;
        dto.objectType = tableModel.getClazz();
        for (int i = 1+start; i <= start+to; i++) {
            var colName = metaData.getColumnLabel(i);
            var columnInfo = tableModel.getColumn(colName);
            if (columnInfo.isEmpty())
                continue;
            dto.columnModels.put(i,columnInfo.get());
        }
        return dto;
    }

    @SneakyThrows
    public Object createInstance(ResultSet result, MappingTableDto createObj) throws InstantiationException, IllegalAccessException, SQLException {
        Object instnace = createObj.objectType.newInstance();
        for (final var entrySet : createObj.columnModels.entrySet())
        {
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

    public class MappingTableDto
    {
        public ColumnModel joinedColumn;
        public Class<?> objectType;
        public TableModel tableModel;
        public HashMap<Integer, ColumnModel> columnModels = new HashMap<Integer, ColumnModel>();
    }
}
