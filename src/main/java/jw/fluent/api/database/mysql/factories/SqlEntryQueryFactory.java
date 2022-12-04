package jw.fluent.api.database.mysql.factories;

import jw.fluent.api.database.api.database_table.models.TableModel;
import jw.fluent.api.database.mysql.query_builder.SqlQueryBuilder;
import jw.fluent.api.database.mysql.models.SqlEntry;

public class SqlEntryQueryFactory {


    public static <T> String deleteQuery(SqlEntry<T> sqlEntity, TableModel tableModel) {
        return SqlQueryBuilder.delete()
                .from(tableModel.getName())
                .where()
                .isEqual(sqlEntity.getKeyColumnName(), sqlEntity.getKey())
                .getQuery();
    }

    public static <T>  String insertQuery(SqlEntry<T> sqlEntity, TableModel tableModel) {

        var columnNames = sqlEntity.getFieldValues().keySet().toArray(new String[1]);
        var values = sqlEntity.getFieldValues().values().toArray(new Object[1]);
        return SqlQueryBuilder.insert()
                .table(tableModel.getName())
                .columns(columnNames)
                .values(values)
                .getQuery();
    }

    public static <T>  String updateQuery(SqlEntry<T> sqlEntity, TableModel tableModel) {

        return SqlQueryBuilder.update()
                .table(tableModel.getName())
                .set(sqlEntity.getUpdatedFields())
                .where()
                .isEqual(sqlEntity.getKeyColumnName(), sqlEntity.getKey())
                .getQuery();
    }
}
