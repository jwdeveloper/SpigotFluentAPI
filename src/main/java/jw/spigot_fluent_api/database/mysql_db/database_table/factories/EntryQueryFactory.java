package jw.spigot_fluent_api.database.mysql_db.database_table.factories;

import jw.spigot_fluent_api.database.mysql_db.database_table.SqlEntry;
import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlQueryBuilder;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;

public class EntryQueryFactory {


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
